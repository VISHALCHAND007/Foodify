package com.example.foodify.cartPage

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.VolleyError
import com.example.foodify.R
import com.example.foodify.VolleyRequest
import com.example.foodify.adapters.MenuItemAdapter
import com.example.foodify.homeActivity.MainActivity
import com.example.foodify.model.MenuItemModel
import com.example.foodify.utilities.ApiUrl
import com.example.foodify.utils.Constants
import com.example.foodify.utils.SharedPreferencesHelper
import org.json.JSONArray
import org.json.JSONObject

class CartActivity : AppCompatActivity() {
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var restaurantNameTv: TextView
    private lateinit var menuItemRv: RecyclerView
    private lateinit var restaurantName: String
    private lateinit var menuList: java.util.ArrayList<MenuItemModel>
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var menuItemAdapter: MenuItemAdapter
    private lateinit var placeOrderBtn: Button
    private var total: Int = 0
    private lateinit var volleyRequest: VolleyRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        init()
    }

    private fun init() {
        initElements()
        getIntentElements()
        initTasks()
        initListeners()
        getAndSetData()
    }

    private fun getIntentElements() {
        restaurantName = intent.getStringExtra("restaurantName").toString()
        val bundle = intent.getBundleExtra("bundle")

        if (bundle != null) {
            menuList = bundle.getParcelableArrayList("menuItems")!!
        }
        settingUpTheButton()
    }

    @SuppressLint("SetTextI18n")
    private fun settingUpTheButton() {
        //getting the order total
        for (i in 0 until menuList.size) {
            total += Integer.parseInt(menuList[i].menuItemPrice.toString())
        }
        placeOrderBtn.text = "Place Order (Total: Rs. $total)"
    }

    private fun initElements() {
        toolbar = findViewById(R.id.toolbar)
        menuItemRv = findViewById(R.id.menuItemRv)
        restaurantNameTv = findViewById(R.id.restaurantName)
        linearLayoutManager = LinearLayoutManager(this@CartActivity)
        placeOrderBtn = findViewById(R.id.placeOrderBtn)
        volleyRequest = VolleyRequest()
    }

    private fun initTasks() {
        toolbar.title = "My Cart"
        restaurantNameTv.text = restaurantName
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initListeners() {
        placeOrderBtn.setOnClickListener {
            val restaurantId = intent.getStringExtra("restaurantId")
            val userId = SharedPreferencesHelper().getVariableInPreferences(
                Constants().USER_ID,
                this@CartActivity
            )

            try {
                val body = JSONObject()
                body.put("user_id", userId)
                body.put("restaurant_id", restaurantId)
                body.put("total_cost", total)

                val food = JSONArray()
                for (i in 0 until menuList.size) {
                    val jsonObject = JSONObject()
                    jsonObject.put("food_item_id", menuList[i].menuItemId)

                    food.put(jsonObject)
                }
                body.put("food", food)

                hitOrderAPI(body)

            } catch (e: java.lang.Exception) {
                Toast.makeText(this@CartActivity, e.message, Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun hitOrderAPI(body: JSONObject) {
        volleyRequest.makePostRequest(ApiUrl().PLACE_ORDER, body, this@CartActivity)
        volleyRequest.setVolleyRequestlistener(object : VolleyRequest.VolleyRequestListener {
            override fun onDataLoaded(jsonObject: JSONObject) {
                if (jsonObject.has("data")) {
                    val json = jsonObject.getJSONObject("data")
                    if (json.has("success")) {
                        val success = jsonObject.getJSONObject("data").getBoolean("success")
                        if (success) {
                            orderSuccessDialog()
                        } else {
                            showToastAndNavigateToHome()
                        }
                    }
                }
            }

            override fun onError(volleyError: VolleyError) {
                showToastAndNavigateToHome()
            }

        })
    }

    private fun showToastAndNavigateToHome() {
        Toast.makeText(this@CartActivity, "Some error occurred!", Toast.LENGTH_SHORT).show()
        val intent = Intent(this@CartActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun getAndSetData() {
        menuItemAdapter = MenuItemAdapter(this@CartActivity, menuList)
        menuItemRv.layoutManager = linearLayoutManager
        menuItemRv.adapter = menuItemAdapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun orderSuccessDialog() {
        val dialog = Dialog(this@CartActivity)
        dialog.setContentView(R.layout.dialog_layout)
        dialog.setCancelable(false)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

        val okBtn: Button = dialog.findViewById(R.id.okBtn)
        okBtn.setOnClickListener {
            val intent = Intent(this@CartActivity, MainActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
    }
}