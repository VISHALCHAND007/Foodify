package com.example.foodify.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodify.R
import com.example.foodify.adapters.FavouriteRestaurantAdapter
import com.example.foodify.database.Entity
import com.example.foodify.database.RestaurantDbHelper

class FavouriteFragment : Fragment() {
    private lateinit var favouriteRecyclerView: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var restaurantListAdapter: FavouriteRestaurantAdapter
    private lateinit var restaurantList: List<Entity>
    private lateinit var progressRl: RelativeLayout

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
        progressRl = view.findViewById(R.id.progressRl)
    }

    private fun initTasks() {
        Handler(Looper.myLooper()!!).postDelayed( {
            progressRl.visibility = View.GONE
            //get favourite restaurant list
            restaurantList = RestaurantDbHelper.FetchProducts(requireContext()).execute().get()

            if (restaurantList.isEmpty())
                fragmentManager?.beginTransaction()?.replace(R.id.frameLayout, HomeFragment())?.commit()

            if (restaurantList.isNotEmpty()) {
                restaurantListAdapter = FavouriteRestaurantAdapter(requireContext(), restaurantList)
                favouriteRecyclerView.layoutManager = linearLayoutManager
                favouriteRecyclerView.adapter = restaurantListAdapter
            } else
                Toast.makeText(requireContext(), "No favourite restaurant", Toast.LENGTH_SHORT).show()
        }, 1000)

    }
}