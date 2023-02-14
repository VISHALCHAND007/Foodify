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
import com.example.foodify.homeActivity.MainActivity
import com.example.foodify.restaurantMenu.RestaurantMenuActivity

class FavouriteRestaurantAdapter(private val mContext: Context, private var mList: List<Entity>) :
    RecyclerView.Adapter<FavouriteRestaurantAdapter.FavouriteRestaurantViewHolder>() {
    private val DELETE = 2
    private val CHECK_EXIST = 3

    class FavouriteRestaurantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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
    ): FavouriteRestaurantViewHolder {
        val view = LayoutInflater.from(mContext)
            .inflate(R.layout.sample_layout_restaurant_list, parent, false)
        return FavouriteRestaurantViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: FavouriteRestaurantViewHolder, position: Int) {
        val restaurant = mList[position]
        val entity = Entity(
            restaurant.restaurantId,
            restaurant.restaurantImg,
            restaurant.restaurantName,
            restaurant.restaurantPrice,
            restaurant.restaurantRating
        )
        val checkExist = RestaurantDbHelper(mContext, entity, CHECK_EXIST).execute().get()
        holder.restaurantName.text = restaurant.restaurantName
        Glide.with(mContext).load(restaurant.restaurantImg).error(R.drawable.chef)
            .into(holder.restaurantImg)
        val cost = restaurant.restaurantPrice + "/person"
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
            RestaurantDbHelper(mContext, entity, DELETE).execute()
            mList = RestaurantDbHelper.FetchProducts(mContext).execute().get()
            //checking if list is empty and if so then navigating to home page
            if (mList.isEmpty()) {
                navigateToHome()
            }
            notifyDataSetChanged()
        }
    }

    private fun navigateToHome() {
        Toast.makeText(mContext, "Favourite list is empty", Toast.LENGTH_SHORT).show()
        val intent = Intent(mContext, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        mContext.startActivity(intent)
    }
}