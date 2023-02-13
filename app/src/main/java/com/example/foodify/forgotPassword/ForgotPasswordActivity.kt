package com.example.foodify.forgotPassword

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.VolleyError
import com.example.foodify.R
import com.example.foodify.VolleyRequest
import com.example.foodify.resetPasswod.ResetPasswordActivity
import com.example.foodify.utilities.ApiUrl
import org.json.JSONObject

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var mobileNumber: EditText
    private lateinit var emailAddress: EditText
    private lateinit var nextBtn: Button
    private lateinit var volleyRequestForgotPassword: VolleyRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        init()
    }

    private fun init() {
        initElement()
        initTasks()
        initListeners()
    }

    private fun initElement() {
        mobileNumber = findViewById(R.id.mobile_number)
        emailAddress = findViewById(R.id.email_address)
        nextBtn = findViewById(R.id.next)
        volleyRequestForgotPassword = VolleyRequest()
    }

    private fun initTasks() {
        supportActionBar?.hide()
    }

    private fun initListeners() {
        nextBtn.setOnClickListener {
            if (checkStatus()) {
                val mobileNumber = mobileNumber.text.toString()
                val email = emailAddress.text.toString()
                val forgotPassJson = JSONObject()
                forgotPassJson.put("mobile_number", mobileNumber)
                forgotPassJson.put("email", email)

                volleyRequestForgotPassword.makePostRequest(
                    ApiUrl().FORGOT_PASSWORD_SEND_OTP,
                    forgotPassJson,
                    this@ForgotPasswordActivity
                )
                volleyRequestForgotPassword.setVolleyRequestlistener(object :
                    VolleyRequest.VolleyRequestListener {
                    override fun onDataLoaded(jsonObject: JSONObject) {
                        if (jsonObject.has("success") && jsonObject.getBoolean("success")) {
                            val intent = Intent(
                                this@ForgotPasswordActivity,
                                ResetPasswordActivity::class.java
                            )
                            intent.putExtra("mobile_number", mobileNumber)
                            startActivity(intent)
                        } else if (jsonObject.has("errorMessage") && jsonObject.getString("errorMessage")
                                .equals("No user found!")
                        ) {
                            makeToast(jsonObject.getString("errorMessage"))
                        }else {
                            makeToast("No user found")
                        }
                    }
                    override fun onError(volleyError: VolleyError) {
                        makeToast("Something went wrong")
                    }

                })
            }
        }
    }

    private fun checkStatus(): Boolean {
        var result = false
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