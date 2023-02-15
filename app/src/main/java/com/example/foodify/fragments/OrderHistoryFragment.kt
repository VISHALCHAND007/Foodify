package com.example.foodify.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.VolleyError
import com.example.foodify.R
import com.example.foodify.VolleyRequest
import com.example.foodify.adapters.OrderHistoryAdapter
import com.example.foodify.model.MenuItemModel
import com.example.foodify.model.OrderHistoryModel
import com.example.foodify.utilities.ApiUrl
import com.example.foodify.utils.Constants
import com.example.foodify.utils.SharedPreferencesHelper
import org.json.JSONObject

class OrderHistoryFragment : Fragment() {
    private lateinit var progressRl: RelativeLayout
    private lateinit var noOrdersFoundTv: TextView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var orderHistoryAdapter: OrderHistoryAdapter
    private lateinit var orderHistoryRv: RecyclerView
    private lateinit var volleyRequest: VolleyRequest
    private lateinit var orderHistoryList: java.util.ArrayList<OrderHistoryModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_order_history, container, false)
        init(view)
        return view
    }

    private fun init(view: View) {
        initElements(view)
        getData()
    }

    private fun initElements(view: View) {
        progressRl = view.findViewById(R.id.progressLayout)
        noOrdersFoundTv = view.findViewById(R.id.noOrdersFoundTv)
        orderHistoryRv = view.findViewById(R.id.orderHistoryRv)
        volleyRequest = VolleyRequest()
        linearLayoutManager = LinearLayoutManager(requireContext())
        orderHistoryList = ArrayList()
    }

    private fun getData() {
        val userId = SharedPreferencesHelper().getVariableInPreferences(
            Constants().USER_ID,
            requireContext()
        )
        volleyRequest.makeGetRequest(ApiUrl().ORDER_HISTORY + "$userId/", null, requireContext())
        volleyRequest.setVolleyRequestlistener(object : VolleyRequest.VolleyRequestListener {
            override fun onDataLoaded(jsonObject: JSONObject) {
                if (jsonObject.has("data")) {
                    val data = jsonObject.getJSONObject("data")
                    if (data.has("success")) {
                        val success = data.getBoolean("success")
                        if (success) {
                            val dataArray = data.getJSONArray("data")
                            for (i in 0 until dataArray.length()) {
                                val dataObj = dataArray.getJSONObject(i)
                                //menu item json array
                                val itemArray = dataObj.getJSONArray("food_items")
                                //creating item arraylist
                                val list = java.util.ArrayList<MenuItemModel>()
                                for (j in 0 until itemArray.length()) {
                                    val itemObj = itemArray.getJSONObject(j)
                                    //creating each menuItem
                                    val menuItem = MenuItemModel(
                                        itemObj.getString("name"), //name
                                        itemObj.getString("food_item_id"), //item id
                                        itemObj.getString("cost") //cost
                                    )
                                    list.add(menuItem)
                                }

                                val item = OrderHistoryModel(
                                    dataObj.getString("restaurant_name"),
                                    dataObj.getString("order_placed_at"),
                                    list
                                )
                                orderHistoryList.add(item)
                            }
                            setData()
                        } else {
                            showErrorToast("Some error occurred")
                        }
                    }
                }
            }

            override fun onError(volleyError: VolleyError) {
                showErrorToast(volleyError.message.toString())
            }
        })
    }

    private fun setData() {
        progressRl.visibility = View.GONE
        if (orderHistoryList.isNotEmpty()) {
            noOrdersFoundTv.visibility = View.GONE
            orderHistoryAdapter = OrderHistoryAdapter(requireContext(), orderHistoryList)
            orderHistoryRv.layoutManager = linearLayoutManager
            orderHistoryRv.adapter = orderHistoryAdapter
        }

    }

    private fun showErrorToast(text: String) {
        Toast.makeText(
            requireContext(),
            text,
            Toast.LENGTH_SHORT
        ).show()
    }
}