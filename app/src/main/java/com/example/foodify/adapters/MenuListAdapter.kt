package com.example.foodify.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodify.R
import com.example.foodify.model.RestaurantMenuModel
import org.w3c.dom.Text

class MenuListAdapter(
    private val mContext: Context,
    private val mList: ArrayList<RestaurantMenuModel>
) : RecyclerView.Adapter<MenuListAdapter.MenuViewHolder>() {
    class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val menuItemName: TextView = itemView.findViewById(R.id.itemName)
        val menuItemPrice: TextView = itemView.findViewById(R.id.price)
        val addButton: Button = itemView.findViewById(R.id.addBtn)
        val indexTv: TextView = itemView.findViewById(R.id.index)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view =
            LayoutInflater.from(mContext).inflate(R.layout.sample_layout_menu_list, parent, false)
        return MenuViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val menuItem = mList[position]
        var index = position + 1;
        holder.indexTv.text = index.toString()
        holder.menuItemName.text = menuItem.itemName
        val price = "Rs. " + menuItem.itemCost
        holder.menuItemPrice.text = price
    }

}