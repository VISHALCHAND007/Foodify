package com.example.foodify.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.VolleyError
import com.example.foodify.R
import com.example.foodify.VolleyRequest
import com.example.foodify.homeActivity.MainActivity
import com.example.foodify.utilities.ApiUrl
import org.json.JSONObject

class SignUpActivity : AppCompatActivity() {
    private lateinit var nameEt: EditText
    private lateinit var emailEt: EditText
    private lateinit var mobileNumberEt: EditText
    private lateinit var deliveryAddressEt: EditText
    private lateinit var passwordEt: EditText
    private lateinit var confirmPasswordEt: EditText
    private lateinit var registerBtn: Button
    private lateinit var nameStr: String
    private lateinit var emailStr: String
    private lateinit var mobileNumberStr: String
    private lateinit var deliveryAddressStr: String
    private lateinit var passwordStr: String
    private lateinit var confirmPasswordStr: String
    private lateinit var volleyRequestRegisterUser: VolleyRequest


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        init()
    }

    private fun init() {
        initElements()
        initTasks()
        initListeners()
    }

    private fun initElements() {
        nameEt = findViewById(R.id.username)
        emailEt = findViewById(R.id.email_address)
        mobileNumberEt = findViewById(R.id.mobile_number)
        deliveryAddressEt = findViewById(R.id.delivery_address)
        passwordEt = findViewById(R.id.password)
        confirmPasswordEt = findViewById(R.id.confirm_password)
        registerBtn = findViewById(R.id.register)
        volleyRequestRegisterUser = VolleyRequest()
    }

    private fun initTasks() {
        supportActionBar?.title = "Register Yourself"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun initListeners() {
        registerBtn.setOnClickListener {
            getValues()
            if (checkFields()) {
                val registerUserJson = JSONObject()
                registerUserJson.put("name", nameStr)
                registerUserJson.put("mobile_number", mobileNumberStr)
                registerUserJson.put("password", passwordStr)
                registerUserJson.put("address", deliveryAddressStr)
                registerUserJson.put("email", emailStr)
                volleyRequestRegisterUser.makePostRequest(
                    ApiUrl().REGISTER_USER,
                    registerUserJson,
                    this
                )
                volleyRequestRegisterUser.setVolleyRequestlistener(object :
                    VolleyRequest.VolleyRequestListener {
                    override fun onDataLoaded(jsonObject: JSONObject) {
                        if (jsonObject.has("success") && jsonObject.getBoolean("success")) {
                            goToHome()
                        } else if (jsonObject.has("errorMessage") && jsonObject.getString("errorMessage").contains("already")) {
                            makeToast("User already registered.")
                        }
                    }

                    override fun onError(volleyError: VolleyError) {
                        makeToast("Something went wrong")
                    }

                })
            }
        }
    }

    private fun goToHome() {
        val intent = Intent(this@SignUpActivity, MainActivity::class.java)
        intent.putExtra("name", nameStr)
        intent.putExtra("email", emailStr)
        intent.putExtra("mobile", mobileNumberStr)
        intent.putExtra("address", deliveryAddressStr)
        startActivity(intent)
    }

    private fun getValues() {
        nameStr = nameEt.text.toString()
        emailStr = emailEt.text.toString()
        mobileNumberStr = mobileNumberEt.text.toString()
        deliveryAddressStr = deliveryAddressEt.text.toString()
        passwordStr = passwordEt.text.toString()
        confirmPasswordStr = confirmPasswordEt.text.toString()
    }

    private fun checkFields(): Boolean {
        var result = false

        if (nameStr == "")
            nameEt.error = "Enter name"
        else if (emailStr == "")
            emailEt.error = "Enter email"
        else if (mobileNumberStr == "")
            mobileNumberEt.error = "Enter mobile number"
        else if (deliveryAddressStr == "")
            deliveryAddressEt.error = "Enter delivery address"
        else if (passwordStr == "")
            passwordEt.error = "Enter password"
        else if (confirmPasswordStr == "")
            confirmPasswordEt.error = "Re-enter password"
        else if (passwordStr != confirmPasswordStr)
            makeToast("Password doesn't match!")
        else
            result = true

        return result
    }

    private fun makeToast(str: String) {
        Toast.makeText(this@SignUpActivity, str, Toast.LENGTH_SHORT).show()
    }
}