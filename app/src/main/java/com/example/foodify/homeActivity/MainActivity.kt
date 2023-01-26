package com.example.foodify.homeActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.foodify.R

class MainActivity : AppCompatActivity() {
    private lateinit var userName: String
    private lateinit var userEmail: String
    private lateinit var mobileNumber: String
    private lateinit var deliveryAddress: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
        getIntentValue()
        initElements()
        initTasks()
        initListeners()
    }

    private fun initElements() {

    }

    private fun getIntentValue() {
        userName = intent.getStringExtra("name").toString()
        userEmail = intent.getStringExtra("email").toString()
        mobileNumber = intent.getStringExtra("mobile").toString()
        deliveryAddress = intent.getStringExtra("address").toString()
    }

    private fun initTasks() {

    }

    private fun initListeners() {

    }
}