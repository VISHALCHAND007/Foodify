package com.example.foodify.splashScreen

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.foodify.R
import com.example.foodify.homeActivity.MainActivity
import com.example.foodify.signin.SignInActivity
import com.example.foodify.utils.Constants
import com.example.foodify.utils.SharedPreferencesHelper

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        init()
    }
    private fun init() {
        initTasks()
    }

    private fun initTasks() {
        supportActionBar?.hide()
        val isUserLoggedIn = SharedPreferencesHelper().getBooleanInPreferences(Constants().IS_LOGGED_IN, this@SplashScreen)
        var intent: Intent
        Handler(mainLooper).postDelayed({
            intent = if(isUserLoggedIn) {
                Intent(this, MainActivity::class.java)
            } else {
                Intent(this, SignInActivity::class.java)
            }
            startActivity(intent)
        }, 3000)
    }
}