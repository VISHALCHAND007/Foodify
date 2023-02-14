package com.example.foodify.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodify.R
import com.example.foodify.model.MenuItemModel

class MenuItemAdapter(private val mContext: Context, private val mList: ArrayList<MenuItemModel>) : RecyclerView.Adapter<MenuItemAdapter.MenuViewHolder>() {
    class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val menuItemTv: TextView = itemView.findViewById(R.id.menuItemTv)
        val menuItemCostTv: TextView = itemView.findViewById(R.id.itemCostTv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.sample_layout_items, parent, false)
        return MenuViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val price = "Rs. "+ mList[position].menuItemPrice
        holder.menuItemTv.text = mList[position].menuItemName
        holder.menuItemCostTv.text = price
    }
}