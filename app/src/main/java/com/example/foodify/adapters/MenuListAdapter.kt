package com.example.foodify.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.foodify.R
import com.example.foodify.cartPage.CartActivity
import com.example.foodify.model.MenuItemModel
import com.example.foodify.model.RestaurantMenuModel

class MenuListAdapter(
    private val mContext: Context,
    private val mList: ArrayList<RestaurantMenuModel>,
    private val proceedToCartBtn: Button,
    private val restaurantName: String
) : RecyclerView.Adapter<MenuListAdapter.MenuViewHolder>() {
    private lateinit var orderList: java.util.ArrayList<MenuItemModel>

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
        orderList = ArrayList()

        val index = position + 1
        holder.indexTv.text = index.toString()
        holder.menuItemName.text = menuItem.itemName
        val price = "Rs. " + menuItem.itemCost
        holder.menuItemPrice.text = price

        holder.addButton.setOnClickListener {
            changeButtonColor(holder)
            val menuItems = MenuItemModel(
                menuItem.itemName,
                menuItem.itemId,
                menuItem.itemCost
            )
            if (!orderList.contains(menuItems))
                orderList.add(menuItems)
            else
                orderList.remove(menuItems)

            handleBtnVisibility()
        }
        proceedToCartBtn.setOnClickListener {

            val intent = Intent(mContext, CartActivity::class.java)
            intent.putExtra("restaurantName", restaurantName)
            intent.putExtra("restaurantId", menuItem.restaurantId)
            val args = Bundle()
            args.putSerializable("menuItems", orderList)
            intent.putExtra("bundle", args)
            mContext.startActivity(intent)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun changeButtonColor(holder: MenuViewHolder) {
        val text = holder.addButton.text.toString().trim()
        if (text == "Add") {
            holder.addButton.text = "Remove"
            holder.addButton.background =
                ContextCompat.getDrawable(mContext, R.drawable.button_rounded_yellow)

        } else {
            holder.addButton.text = "Add"
            holder.addButton.background =
                ContextCompat.getDrawable(mContext, R.drawable.button_round_corners)
        }
    }

    private fun handleBtnVisibility() {
        if (orderList.size > 0)
            proceedToCartBtn.visibility = View.VISIBLE
        else
            proceedToCartBtn.visibility = View.GONE
    }
}