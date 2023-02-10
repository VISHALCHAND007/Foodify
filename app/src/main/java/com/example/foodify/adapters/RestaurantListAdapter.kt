package com.example.foodify.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodify.R
import java.util.ArrayList

class RestaurantListAdapter(val mList: ArrayList<String>?, val mContext: Context) :
    RecyclerView.Adapter<RestaurantListAdapter.RestaurantViewHolder>() {


    class RestaurantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val restaturantImg: ImageView = itemView.findViewById(R.id.restaurantImg)
        val restaurantName: TextView = itemView.findViewById(R.id.restaurantName)
        val restaurantPerPersonCost: TextView = itemView.findViewById(R.id.restaurantPerPersonCost)
        val favouriteImg: ImageView = itemView.findViewById(R.id.favouriteImg)
        val ratingTv: TextView = itemView.findViewById(R.id.ratingTv)

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

    }

    override fun getItemCount(): Int {
//        return mList.size
        return 10
    }
}