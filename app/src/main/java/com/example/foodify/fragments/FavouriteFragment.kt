package com.example.foodify.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide.init
import com.example.foodify.R
import com.example.foodify.adapters.RestaurantListAdapter
import com.example.foodify.database.Entity
import com.example.foodify.database.RestaurantDbHelper
import com.example.foodify.model.RestaurantListModel

class FavouriteFragment : Fragment() {
    private lateinit var favouriteRecyclerView: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var restaurantListAdapter: RestaurantListAdapter
    private lateinit var restaurantList: List<Entity>
    val GET_ALL_RESTAURANTS = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favourite, container, false)
        init(view)
        return view
    }
    private fun init(view: View) {
        initElements(view)
        initTasks()
    }

    private fun initElements(view: View) {
        favouriteRecyclerView = view.findViewById(R.id.favRestaurantRv)
        linearLayoutManager = LinearLayoutManager(requireContext())
    }

    private fun initTasks() {
        //get favourite restaurant list
        restaurantList = RestaurantDbHelper.FetchProducts(requireContext()).execute().get()

        if(restaurantList.isNotEmpty()) {
//            restaurantListAdapter = RestaurantListAdapter(restaurantList, requireContext())
//            favouriteRecyclerView.layoutManager = linearLayoutManager
//            favouriteRecyclerView.adapter = restaurantListAdapter
        }
    }
}