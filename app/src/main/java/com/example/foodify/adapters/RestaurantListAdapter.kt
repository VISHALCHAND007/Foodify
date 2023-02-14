package com.example.foodify.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodify.R
import com.example.foodify.database.Entity
import com.example.foodify.database.RestaurantDbHelper
import com.example.foodify.model.RestaurantListModel
import com.example.foodify.restaurantMenu.RestaurantMenuActivity
import kotlinx.coroutines.NonDisposableHandle.parent
import java.util.ArrayList

class RestaurantListAdapter(
    private val mList: ArrayList<RestaurantListModel>?,
    private val mContext: Context
) :
    RecyclerView.Adapter<RestaurantListAdapter.RestaurantViewHolder>() {
    private val INSERT = 1
    private val DELETE = 2
    private val CHECK_EXIST = 3

    class RestaurantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val restaurantImg: ImageView = itemView.findViewById(R.id.restaurantImg)
        val restaurantName: TextView = itemView.findViewById(R.id.restaurantName)
        val restaurantPerPersonCost: TextView = itemView.findViewById(R.id.restaurantPerPersonCost)
        val favouriteImg: ImageView = itemView.findViewById(R.id.favouriteImg)
        val ratingTv: TextView = itemView.findViewById(R.id.ratingTv)
        val restaurantItemCl: ConstraintLayout = itemView.findViewById(R.id.restaurantItemCl)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RestaurantViewHolder {
        val view = LayoutInflater.from(mContext)
            .inflate(R.layout.sample_layout_restaurant_list, parent, false)
        return RestaurantViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(
        holder: RestaurantViewHolder,
        position: Int
    ) {
        val restaurant = mList?.get(position)
        if (restaurant != null) {
            val entity = Entity(
                restaurant.restaurantId,
                restaurant.restaurantImg,
                restaurant.restaurantName,
                restaurant.costForOne,
                restaurant.restaurantRating
            )
            val checkExist = RestaurantDbHelper(mContext, entity, CHECK_EXIST).execute().get()
            holder.restaurantName.text = restaurant.restaurantName
            Glide.with(mContext).load(restaurant.restaurantImg).error(R.drawable.chef)
                .into(holder.restaurantImg)
            val cost = restaurant.costForOne + "/person"
            holder.restaurantPerPersonCost.text = cost
            holder.ratingTv.text = restaurant.restaurantRating
            if (checkExist)
                holder.favouriteImg.setImageResource(R.drawable.heart)
            else
                holder.favouriteImg.setImageResource(R.drawable.favourite)

            //on a restaurant selected listener
            holder.restaurantItemCl.setOnClickListener {
                val intent = Intent(mContext, RestaurantMenuActivity::class.java)
                intent.putExtra("restaurantName", restaurant.restaurantName)
                intent.putExtra("restaurantId", restaurant.restaurantId)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                mContext.startActivity(intent)
            }
            //favorite button functionality
            holder.favouriteImg.setOnClickListener {

                if (checkExist) {
                    RestaurantDbHelper(mContext, entity, DELETE).execute()
                    holder.favouriteImg.setImageResource(R.drawable.favourite)
                } else {
                    RestaurantDbHelper(mContext, entity, INSERT).execute()
                    holder.favouriteImg.setImageResource(R.drawable.heart)
                }
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int {
        return mList!!.size
    }
}