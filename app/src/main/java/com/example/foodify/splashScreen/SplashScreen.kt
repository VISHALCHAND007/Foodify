package com.example.foodify.splashScreen

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.foodify.R
import com.example.foodify.signin.SignInActivity

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
        Handler(mainLooper).postDelayed({
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }, 3000)
    }
}