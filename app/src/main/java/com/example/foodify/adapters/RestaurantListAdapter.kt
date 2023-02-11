package com.example.foodify.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodify.R
import com.example.foodify.model.RestaurantListModel
import com.example.foodify.restaurantMenu.RestaurantMenuActivity
import java.util.ArrayList

class RestaurantListAdapter(private val mList: ArrayList<RestaurantListModel>?, private val mContext: Context) :
    RecyclerView.Adapter<RestaurantListAdapter.RestaurantViewHolder>() {


    class RestaurantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val restaturantImg: ImageView = itemView.findViewById(R.id.restaurantImg)
        val restaurantName: TextView = itemView.findViewById(R.id.restaurantName)
        val restaurantPerPersonCost: TextView = itemView.findViewById(R.id.restaurantPerPersonCost)
        val favouriteImg: ImageView = itemView.findViewById(R.id.favouriteImg)
        val ratingTv: TextView = itemView.findViewById(R.id.ratingTv)
        val restaurantItemCl: ConstraintLayout = itemView.findViewById(R.id.restaurantItemCl)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RestaurantListAdapter.RestaurantViewHolder {
        val view = LayoutInflater.from(mContext)
            .inflate(R.layout.sample_layout_restaurant_list, parent, false)
        return RestaurantViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: RestaurantListAdapter.RestaurantViewHolder,
        position: Int
    ) {
        val restaurant = mList?.get(position)
        if (restaurant != null) {
            holder.restaurantName.text = restaurant.restaurantName
            Glide.with(mContext).load(restaurant.restaurantImg).error(R.drawable.chef).into(holder.restaturantImg)
            val cost = restaurant.costForOne + "/person"
            holder.restaurantPerPersonCost.text = cost
            holder.ratingTv.text = restaurant.restaurantRating

            //on a restaurant selected listener
            holder.restaurantItemCl.setOnClickListener {
                val intent = Intent(mContext, RestaurantMenuActivity::class.java)
                intent.putExtra("restaurantName", restaurant.restaurantName)
                intent.putExtra("restaurantId", restaurant.restaurantId)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                mContext.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return mList!!.size
    }
}