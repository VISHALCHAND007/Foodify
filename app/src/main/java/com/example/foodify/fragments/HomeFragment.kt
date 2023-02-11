package com.example.foodify.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.VolleyError
import com.example.foodify.R
import com.example.foodify.VolleyRequest
import com.example.foodify.adapters.RestaurantListAdapter
import com.example.foodify.model.RestaurantListModel
import com.example.foodify.utilities.ApiUrl
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var restaurantRv: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var restaurantListAdapter: RestaurantListAdapter
    private lateinit var restaurantList: java.util.ArrayList<RestaurantListModel>
    private lateinit var volleyRequest: VolleyRequest
    private lateinit var progressRl: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        init(view)
        return view
    }

    private fun init(view: View) {
        initElements(view)
        initTasks()
        getRestaurantList()
        initListeners()
    }

    private fun initElements(view: View) {
        restaurantRv = view.findViewById(R.id.restaurantRv)
        linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        restaurantList = arrayListOf()
        volleyRequest = VolleyRequest()
        progressRl = view.findViewById(R.id.progressRl)
    }

    private fun initTasks() {
    }

    private fun getRestaurantList() {
        volleyRequest.makeGetRequest(ApiUrl().FETCH_RESTAURANTS, null, requireContext())
        volleyRequest.setVolleyRequestlistener(object : VolleyRequest.VolleyRequestListener {
            override fun onDataLoaded(jsonObject: JSONObject) {
                if (jsonObject.has("data")) {
                    val dataObj = jsonObject.getJSONObject("data")
                    if (dataObj.has("success")) {
                        val success = dataObj.getBoolean("success")
                        if (success) {
                            progressRl.visibility = View.GONE
                            val jsonArray = dataObj.getJSONArray("data")
                            for (i in 0 until jsonArray.length()) {
                                val restaurantObj = jsonArray.getJSONObject(i)
                                val restaurant = RestaurantListModel(
                                    restaurantObj.getString("id"),
                                    restaurantObj.getString("name"),
                                    restaurantObj.getString("rating"),
                                    restaurantObj.getString("cost_for_one"),
                                    restaurantObj.getString("image_url")
                                )
                                restaurantList.add(restaurant)
                            }
                            setHomeRestaurantsList()
                        } else {
                            Toast.makeText(requireContext(), R.string.error_msg, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }

            override fun onError(volleyError: VolleyError) {
                Toast.makeText(requireContext(), R.string.error_msg, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun setHomeRestaurantsList() {
        restaurantListAdapter = RestaurantListAdapter(restaurantList, requireContext())
        restaurantRv.layoutManager = linearLayoutManager
        restaurantRv.adapter = restaurantListAdapter
    }

    private fun initListeners() {
    }
}