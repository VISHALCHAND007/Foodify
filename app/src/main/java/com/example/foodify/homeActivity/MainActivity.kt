package com.example.foodify.homeActivity

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.foodify.fragments.HomeFragment
import com.example.foodify.R
import com.example.foodify.signin.SignInActivity
import com.example.foodify.utils.Constants
import com.example.foodify.utils.SharedPreferencesHelper
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var userName: String
    private lateinit var userEmail: String
    private lateinit var mobileNumber: String
    private lateinit var deliveryAddress: String
    private lateinit var toolBar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var userNameTv: TextView
    private lateinit var userImg: ImageView
    private lateinit var userNumberTv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
        initElements()
        getIntentValue()
        initTasks()
        openHomeFragment()
        initListeners()
    }

    private fun initElements() {
        toolBar = findViewById(R.id.toolBar)
        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigationView)
        //to access the components of the header inside navigation view
        userNameTv = navigationView.getHeaderView(0).findViewById(R.id.userNameProfile)
        userNumberTv = navigationView.getHeaderView(0).findViewById(R.id.userNumber)
        userImg = navigationView.getHeaderView(0).findViewById(R.id.userImg)
    }

    private fun getIntentValue() {
        userName = intent.getStringExtra("name").toString()
        userEmail = intent.getStringExtra("email").toString()
        mobileNumber = intent.getStringExtra("mobile").toString()
        deliveryAddress = intent.getStringExtra("address").toString()
        saveIntentValues()
    }

    private fun saveIntentValues() {
        val needToSave = SharedPreferencesHelper().getBooleanInPreferences(Constants().SAVE_DATA, this@MainActivity)
        if(needToSave) {
            SharedPreferencesHelper().setVariableInPreferences(Constants().USER_NAME, userName, this@MainActivity)
            SharedPreferencesHelper().setVariableInPreferences(Constants().USER_EMAIL, userEmail, this@MainActivity)
            SharedPreferencesHelper().setVariableInPreferences(Constants().USER_NUMBER, mobileNumber, this@MainActivity)
            SharedPreferencesHelper().setBooleanInPreferences(Constants().SAVE_DATA, false, this@MainActivity)
        }

    }

    @SuppressLint("SetTextI18n")
    private fun initTasks() {
        setSupportActionBar(toolBar)
        supportActionBar?.title = "All Restaurants"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        addHamburgerIcon()

        //adding the user data on navigation View
        userNameTv.text = SharedPreferencesHelper().getVariableInPreferences(Constants().USER_NAME, this@MainActivity)
        val number = SharedPreferencesHelper().getVariableInPreferences(Constants().USER_NUMBER, this@MainActivity)
        userNumberTv.text = "+91 $number"
    }

    private fun addHamburgerIcon() {
        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this@MainActivity,
            drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )

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
                    clearPreferencesAndNavigateToLoginScreen()
                }
            }

            return@setNavigationItemSelectedListener true
        }
    }

    private fun clearPreferencesAndNavigateToLoginScreen() {
        SharedPreferencesHelper().setVariableInPreferences(Constants().USER_NAME, "",this@MainActivity)
        SharedPreferencesHelper().setVariableInPreferences(Constants().USER_EMAIL, "",this@MainActivity)
        SharedPreferencesHelper().setVariableInPreferences(Constants().USER_NUMBER, "",this@MainActivity)
        SharedPreferencesHelper().setVariableInPreferences(Constants().DELIVERY_ADDRESS, "",this@MainActivity)
        SharedPreferencesHelper().setBooleanInPreferences(Constants().IS_LOGGED_IN, false,this@MainActivity)

        //setting intent
        val intent = Intent(this@MainActivity, SignInActivity::class.java)
        startActivity(intent)
        finish()
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

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val dialog = AlertDialog.Builder(this@MainActivity)
        dialog.setMessage("Do you want to exit ?")
        dialog.setCancelable(false)
        dialog.setPositiveButton("Yes") { _: DialogInterface, _: Int ->
            finishAffinity()
        }
        dialog.setNegativeButton("No") {_ : DialogInterface, _: Int ->
        }
        dialog.show()
    }
}