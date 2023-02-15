package com.example.foodify.searchPage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
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
import java.util.*
import kotlin.collections.ArrayList

class SearchActivity : AppCompatActivity() {
    private lateinit var restaurantList: java.util.ArrayList<RestaurantListModel>
    private lateinit var volleyRequest: VolleyRequest
    private lateinit var restaurantRv: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var restaurantListAdapter: RestaurantListAdapter
    private lateinit var searchEt: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        init()
    }

    private fun init() {
        initElements()
        initTasks()
        getRestaurantList()
        initListeners()
    }

    private fun initElements() {
        restaurantRv = findViewById(R.id.restaurantRv)
        linearLayoutManager =
            LinearLayoutManager(this@SearchActivity, LinearLayoutManager.VERTICAL, false)
        restaurantList = arrayListOf()
        volleyRequest = VolleyRequest()
        searchEt = findViewById(R.id.searchEt)
    }

    private fun initTasks() {
        searchEt.requestFocus()
    }

    private fun getRestaurantList() {
        volleyRequest.makeGetRequest(ApiUrl().FETCH_RESTAURANTS, null, this@SearchActivity)
        volleyRequest.setVolleyRequestlistener(object : VolleyRequest.VolleyRequestListener {
            override fun onDataLoaded(jsonObject: JSONObject) {
                if (jsonObject.has("data")) {
                    val dataObj = jsonObject.getJSONObject("data")
                    if (dataObj.has("success")) {
                        val success = dataObj.getBoolean("success")
                        if (success) {
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
                            Toast.makeText(
                                this@SearchActivity,
                                R.string.error_msg,
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                }
            }

            override fun onError(volleyError: VolleyError) {
                Toast.makeText(this@SearchActivity, R.string.error_msg, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun setHomeRestaurantsList() {
        restaurantListAdapter = RestaurantListAdapter(restaurantList, this@SearchActivity)
        restaurantRv.layoutManager = linearLayoutManager
        restaurantRv.adapter = restaurantListAdapter
    }

    private fun initListeners() {
        searchEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null) {
                    searchProducts(s)
                }
            }

            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    searchProducts(s)
                }
            }
        })
    }

    private fun searchProducts(charseq: CharSequence) {
        val tempList = ArrayList<RestaurantListModel>()

        for(i in 0 until restaurantList.size) {
            if(restaurantList[i].restaurantName.lowercase(Locale.ROOT).contains(charseq.toString().lowercase(Locale.ROOT))) {
                tempList.add(restaurantList[i])
            }
        }
        restaurantListAdapter.filterProducts(tempList)
    }
}