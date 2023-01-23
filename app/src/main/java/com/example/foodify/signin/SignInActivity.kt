package com.example.foodify.signin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.app.ActivityCompat.finishAffinity
import com.android.volley.toolbox.JsonObjectRequest
import com.example.foodify.R
import com.example.foodify.utilities.ApiUrl
import com.example.foodify.volley.VolleyRequest
import com.example.foodify.volley.VolleyRequest.VolleyRequestListener
import org.json.JSONObject

class SignInActivity : AppCompatActivity() {
    private lateinit var mobileNumberEt: EditText
    private lateinit var passwordEt: EditText
    private lateinit var loginBtn: Button
    private lateinit var forgotPasswordTv: TextView
    private lateinit var signUpTv: TextView
    private lateinit var volleyRequest: VolleyRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        init()
    }

    private fun init() {
        initElements()
        initTasks()
        initListeners()
    }

    private fun initElements() {
        mobileNumberEt = findViewById(R.id.mobile_number)
        passwordEt = findViewById(R.id.password)
        loginBtn = findViewById(R.id.login_btn)
        forgotPasswordTv = findViewById(R.id.forgot_password)
        signUpTv = findViewById(R.id.signUp)
        volleyRequest = VolleyRequest()
    }

    private fun initTasks() {
        supportActionBar?.hide()
    }

    private fun initListeners() {
        loginBtn.setOnClickListener {
            if (checkRequiredFields()) run {
                val jsonObject = JSONObject()
                jsonObject.put("mobile_number", mobileNumberEt.text)
                jsonObject.put("password", passwordEt.text)
                volleyRequest.makePostRequest(ApiUrl().LOGIN, jsonObject, this)
                volleyRequest.setVolleyRequestlistener(object : VolleyRequestListener {
                    override fun onDataLoaded(jsonObject: JSONObject) {
                        TODO("Not yet implemented")
                    }

                    override fun onError() {
                        TODO("Not yet implemented")
                    }

                })

            }
        }
    }

    private fun checkRequiredFields(): Boolean {



        return false
    }

    override fun onBackPressed() {
        finishAffinity()
    }
}