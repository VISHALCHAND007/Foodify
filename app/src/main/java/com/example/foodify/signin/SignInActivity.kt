package com.example.foodify.signin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.foodify.R
import com.example.foodify.VolleyRequest
import com.example.foodify.utilities.ApiUrl

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
                val loginJsonObject = JSONObject()
                loginJsonObject.put("mobile_number", mobileNumberEt.text.toString())
                loginJsonObject.put("password", passwordEt.text.toString())
                Log.e("here", loginJsonObject.toString())
                volleyRequest.makePostRequest(ApiUrl().LOGIN, loginJsonObject, this)
                volleyRequest.setVolleyRequestlistener(object: VolleyRequest.VolleyRequestListener{
                    override fun onDataLoaded(jsonObject: JSONObject) {
                        Log.e("here==", jsonObject.toString())
                    }

                    override fun onError() {
                        TODO("Not yet implemented")
                    }

                })
            }
        }
    }

    private fun checkRequiredFields(): Boolean {
        var result = true
        if (mobileNumberEt.text.toString() == "" && passwordEt.text.toString() == "") {
            makeToast("Mobile number and password must not be empty.")
            result = false
        } else if (mobileNumberEt.text.toString() == "") {
            makeToast("Mobile number is required.")
            result = false
        } else if (passwordEt.text.toString() == "") {
            makeToast("Password is required.")
            result = false
        } else if (mobileNumberEt.text.toString().length < 10) {
            mobileNumberEt.error = "Enter a valid mobile number"
        } else if (passwordEt.text.toString().length < 4) {
            passwordEt.error = "Enter a valid password"
        }
        return result
    }

    override fun onBackPressed() {
        finishAffinity()
    }
    fun makeToast(str: String) {
        Toast.makeText(
            this,
            str,
            Toast.LENGTH_SHORT
        ).show()
    }
}