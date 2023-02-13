package com.example.foodify.signin

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.VolleyError
import com.example.foodify.R
import com.example.foodify.VolleyRequest
import com.example.foodify.forgotPassword.ForgotPasswordActivity
import com.example.foodify.homeActivity.MainActivity
import com.example.foodify.signup.SignUpActivity
import com.example.foodify.utilities.ApiUrl
import com.example.foodify.utils.Constants
import com.example.foodify.utils.SharedPreferencesHelper
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
                volleyRequest.makePostRequest(ApiUrl().LOGIN, loginJsonObject, this)
                volleyRequest.setVolleyRequestlistener(object :
                    VolleyRequest.VolleyRequestListener {
                    override fun onDataLoaded(jsonObject: JSONObject) {
                        if (jsonObject.has("data")) {
                            val data = jsonObject.getJSONObject("data")
                            if (data.has("success")) {
                                if (data.getBoolean("success")) {
                                    //go to home
                                   goToHome(jsonObject)
                                } else if (data.has("errorMessage") && data.getString("errorMessage")
                                        .contains("Incorrect")
                                ) {
                                    //show toast of incorrect password
                                    makeToast("Incorrect Password.")
                                } else if (data.has("errorMessage") && data.getString("errorMessage")
                                        .contains("not registered")
                                ) {
                                    showDialog()
                                }
                            }
                        }
                    }

                    override fun onError(volleyError: VolleyError) {
                        makeToast(volleyError.message.toString())
                    }
                })
            }
        }
        forgotPasswordTv.setOnClickListener {
            val intent = Intent(this@SignInActivity, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }
        signUpTv.setOnClickListener {
            val intent = Intent(this@SignInActivity, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showDialog() {
        val alertDialog = AlertDialog.Builder(this@SignInActivity)
        alertDialog.setCancelable(true)
        alertDialog.setMessage("It looks like you don't have an account. Sign up?")
        alertDialog.setTitle("Not registered")
        alertDialog.setPositiveButton("Yes") { _, _ ->
            //user not registered, navigate the screen to sign up page
            val intent = Intent(this@SignInActivity, SignUpActivity::class.java)
            startActivity(intent)
        }
        alertDialog.setNegativeButton("No") { _, _ ->

        }
        alertDialog.show()
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
    private fun goToHome(jsonObject: JSONObject) {
        //set preference value
        SharedPreferencesHelper().setBooleanInPreferences(Constants().IS_LOGGED_IN, true, this@SignInActivity)
        SharedPreferencesHelper().setBooleanInPreferences(Constants().SAVE_DATA, true, this@SignInActivity)

        val dataJSONObject: JSONObject = jsonObject.getJSONObject("data").getJSONObject("data")
        val intent = Intent(this@SignInActivity, MainActivity::class.java)
        intent.putExtra("name", dataJSONObject.getString("name"))
        intent.putExtra("email", dataJSONObject.getString("email"))
        intent.putExtra("mobile", dataJSONObject.getString("mobile_number"))
        intent.putExtra("address", dataJSONObject.getString("address"))
        startActivity(intent)
    }
}