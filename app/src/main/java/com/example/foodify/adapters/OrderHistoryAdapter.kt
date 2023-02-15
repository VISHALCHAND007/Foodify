package com.example.foodify.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodify.R
import com.example.foodify.model.MenuItemModel
import com.example.foodify.model.OrderHistoryModel

class OrderHistoryAdapter(
    private val mContext: Context,
    private val mList: java.util.ArrayList<OrderHistoryModel>
) : RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryViewHolder>() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var menuItemAdapter: MenuItemAdapter

    class OrderHistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val restaurantNameTv: TextView = itemView.findViewById(R.id.restaurantName)
        val orderedDateTv: TextView = itemView.findViewById(R.id.orderedDate)
        val orderedItemsRv: RecyclerView = itemView.findViewById(R.id.menuItemsRv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHistoryViewHolder {
        val view = LayoutInflater.from(mContext)
            .inflate(R.layout.sample_layout_order_history, parent, false)
        return OrderHistoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: OrderHistoryViewHolder, position: Int) {
        holder.restaurantNameTv.text = mList[position].restaurantName
        val date = mList[position].orderedDate.substring(0, 8)
        holder.orderedDateTv.text = date.replace("-", "/", false)
        //creating the arraylist of menuItems
        val menuItems = mList[position].listMenuItems

        //calling the adapter to show these menu items
        setMenuItems(holder, menuItems)
    }

    private fun setMenuItems(
        holder: OrderHistoryViewHolder,
        menuItems: java.util.ArrayList<MenuItemModel>,
    ) {
        linearLayoutManager = LinearLayoutManager(mContext)
        menuItemAdapter = MenuItemAdapter(mContext, menuItems)
        holder.orderedItemsRv.layoutManager = linearLayoutManager
        holder.orderedItemsRv.adapter = menuItemAdapter
    }
}