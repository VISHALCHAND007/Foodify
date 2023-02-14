package com.example.foodify.cartPage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodify.R
import com.example.foodify.adapters.MenuItemAdapter
import com.example.foodify.model.MenuItemModel

class CartActivity : AppCompatActivity() {
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var restaurantNameTv: TextView
    private lateinit var menuItemRv: RecyclerView
    private lateinit var restaurantName: String
    private lateinit var menuList: java.util.ArrayList<MenuItemModel>
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var menuItemAdapter: MenuItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        init()
    }
    private fun init() {
        initElements()
        getIntentElements()
        initTasks()
        getAndSetData()
    }

    private fun getIntentElements() {
        restaurantName = intent.getStringExtra("restaurantName").toString()
        val bundle = intent.getBundleExtra("bundle")

        if (bundle != null) {
            menuList = bundle.getParcelableArrayList("menuItems")!!
        }
    }

    private fun initElements() {
        toolbar = findViewById(R.id.toolbar)
        menuItemRv = findViewById(R.id.menuItemRv)
        restaurantNameTv = findViewById(R.id.restaurantName)
        linearLayoutManager = LinearLayoutManager(this@CartActivity)
    }

    private fun initTasks() {
        toolbar.title = "My Cart"
        restaurantNameTv.text = restaurantName
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun getAndSetData() {
        menuItemAdapter = MenuItemAdapter(this@CartActivity, menuList)
        menuItemRv.layoutManager = linearLayoutManager
        menuItemRv.adapter = menuItemAdapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}