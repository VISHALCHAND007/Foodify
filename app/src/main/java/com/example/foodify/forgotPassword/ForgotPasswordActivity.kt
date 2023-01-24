package com.example.foodify.forgotPassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.foodify.R

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var mobileNumber: TextView
    private lateinit var emailAddress: TextView
    private lateinit var nextBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        init()
    }

    fun init() {
        initElement()
        initTasks()
        initListeners()
    }

    private fun initElement() {
        mobileNumber = findViewById(R.id.mobile_number)
        emailAddress = findViewById(R.id.email_address)
        nextBtn = findViewById(R.id.next)
    }

    private fun initTasks() {
        supportActionBar?.hide()
    }

    private fun initListeners() {
        nextBtn.setOnClickListener {
            if (checkStatus()) {

            }
        }
    }

    private fun checkStatus(): Boolean {
        var result = false;
        if (mobileNumber.text.toString() == "") {
            makeToast("Enter mobile number")
        } else if (emailAddress.text.toString() == "") {
            makeToast("Enter password")
        } else {
            result = true
        }
        return result
    }

    private fun makeToast(str: String) {
        Toast.makeText(this@ForgotPasswordActivity, str, Toast.LENGTH_SHORT).show()
    }
}