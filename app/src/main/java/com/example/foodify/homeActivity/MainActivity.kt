package com.example.foodify.homeActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.CollapsibleActionView
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.foodify.Fragments.HomeFragment
import com.example.foodify.R
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var userName: String
    private lateinit var userEmail: String
    private lateinit var mobileNumber: String
    private lateinit var deliveryAddress: String
    private lateinit var toolBar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
        getIntentValue()
        initElements()
        openHomeFragment()
        initTasks()
        initListeners()
    }

    private fun initElements() {
        toolBar = findViewById(R.id.toolBar)
        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigationView)
    }

    private fun getIntentValue() {
        userName = intent.getStringExtra("name").toString()
        userEmail = intent.getStringExtra("email").toString()
        mobileNumber = intent.getStringExtra("mobile").toString()
        deliveryAddress = intent.getStringExtra("address").toString()
    }

    private fun initTasks() {
        setSupportActionBar(toolBar)
        supportActionBar?.title = "All Restaurants"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        addHamburgerIcon()
    }

    private fun addHamburgerIcon() {
        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this@MainActivity,
            drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        );

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        //this will not set the hamburger icon to work for that we need to set the listener overriding onOptionsItemSelected
    }

    private fun initListeners() {
        navigationView.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.home -> {
                    openHomeFragment()
                    drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.profile -> {

                }
                R.id.favourite_restaurant -> {

                }
                R.id.order_history -> {

                }
                R.id.faq -> {

                }
                R.id.logout -> {

                }
            }

            return@setNavigationItemSelectedListener true
        }
    }

    private fun openHomeFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, HomeFragment())
            .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if(id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START)
        }


        return super.onOptionsItemSelected(item)
    }
}