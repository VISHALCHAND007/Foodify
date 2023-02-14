package com.example.foodify.restaurantMenu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.RelativeLayout
import android.widget.ScrollView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.VolleyError
import com.example.foodify.R
import com.example.foodify.VolleyRequest
import com.example.foodify.adapters.MenuListAdapter
import com.example.foodify.model.RestaurantMenuModel
import com.example.foodify.utilities.ApiUrl
import org.json.JSONObject

class RestaurantMenuActivity : AppCompatActivity() {
    private lateinit var menuRv: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var toolbar: Toolbar
    private lateinit var restaurantName: String
    private lateinit var restaurantId: String
    private lateinit var volleyRequest: VolleyRequest
    private lateinit var menuList: ArrayList<RestaurantMenuModel>
    private lateinit var menuListAdapter: MenuListAdapter
    private lateinit var scrollView: ScrollView
    private lateinit var progressRl: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_menu)
        init()
    }

    private fun init() {
        getIntentValues()
        initElements()
        initTasks()
        getItems()
        initListeners()
    }

    private fun getIntentValues() {
        restaurantName = intent.getStringExtra("restaurantName").toString()
        restaurantId = intent.getStringExtra("restaurantId").toString()
    }

    private fun initElements() {
        menuRv = findViewById(R.id.menuRv)
        linearLayoutManager = LinearLayoutManager(this@RestaurantMenuActivity)
        toolbar = findViewById(R.id.toolBar)
        volleyRequest = VolleyRequest()
        menuList = arrayListOf()
        scrollView = findViewById(R.id.scrollView)
        progressRl = findViewById(R.id.progressRl)
    }

    private fun initTasks() {
        //setting up the toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.title = restaurantName
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        scrollView.isVerticalScrollBarEnabled = false
    }

    private fun getItems() {
        volleyRequest.makeGetRequest(
            ApiUrl().RESTAURANT_DETAILS + restaurantId,
            null,
            this@RestaurantMenuActivity
        )
        volleyRequest.setVolleyRequestlistener(object : VolleyRequest.VolleyRequestListener {
            override fun onDataLoaded(jsonObject: JSONObject) {
                try {
                    if (jsonObject.has("data")) {
                        val dataObject = jsonObject.getJSONObject("data")
                        if (dataObject.has("success")) {
                            val success = dataObject.getBoolean("success")
                            if (success) {
                                progressRl.visibility = View.GONE
                                val jsonArray = dataObject.getJSONArray("data")
                                for (i in 0 until jsonArray.length()) {
                                    val menuObj = jsonArray.getJSONObject(i)
                                    val menuItem = RestaurantMenuModel(
                                        menuObj.getString("id"),
                                        menuObj.getString("name"),
                                        menuObj.getString("cost_for_one"),
                                        menuObj.getString("restaurant_id")
                                    )
                                    menuList.add(menuItem)
                                }
                                setData()
                            }
                        }
                    }
                } catch (e: java.lang.Exception) {
                    Toast.makeText(this@RestaurantMenuActivity, e.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onError(volleyError: VolleyError) {
                Toast.makeText(this@RestaurantMenuActivity, R.string.error_msg, Toast.LENGTH_SHORT)
                    .show()
            }

        })
    }

    private fun setData() {
        menuListAdapter = MenuListAdapter(this@RestaurantMenuActivity, menuList)
        menuRv.layoutManager = linearLayoutManager
        menuRv.adapter = menuListAdapter
    }

    private fun initListeners() {

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            super.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}