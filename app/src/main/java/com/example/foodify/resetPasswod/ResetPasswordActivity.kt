package com.example.foodify.resetPasswod

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.VolleyError
import com.example.foodify.R
import com.example.foodify.VolleyRequest
import com.example.foodify.signin.SignInActivity
import com.example.foodify.utilities.ApiUrl
import org.json.JSONObject

class ResetPasswordActivity : AppCompatActivity() {
    private lateinit var otpEt: EditText
    private lateinit var passwordEt: EditText
    private lateinit var confirmPasswordEt: EditText
    private lateinit var submitBtn: Button
    private lateinit var volleyRequestResetPassword: VolleyRequest
    private lateinit var mobileNumber: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        init()
    }

    private fun init() {
        initElements()
        initTasks()
        initListeners()
    }

    private fun initElements() {
        otpEt = findViewById(R.id.otp)
        passwordEt = findViewById(R.id.password)
        confirmPasswordEt = findViewById(R.id.confirm_password)
        submitBtn = findViewById(R.id.submit)
        volleyRequestResetPassword = VolleyRequest()
    }

    private fun initTasks() {
        supportActionBar?.hide()
        mobileNumber = intent.getStringExtra("mobile_number").toString()
    }

    private fun initListeners() {
        submitBtn.setOnClickListener {
            val password = passwordEt.text.toString()
            val otp = otpEt.text.toString()
            if (checkFields()) {
                val resetPasswordJson = JSONObject()
                resetPasswordJson.put("mobile_number", mobileNumber)
                resetPasswordJson.put("password", password)
                resetPasswordJson.put("otp", otp)

                //hitting the reset API and taking the decision accordingly
                volleyRequestResetPassword.makePostRequest(
                    ApiUrl().VERIFY_OTP,
                    resetPasswordJson,
                    this
                )
                volleyRequestResetPassword.setVolleyRequestlistener(object :
                    VolleyRequest.VolleyRequestListener {
                    override fun onDataLoaded(jsonObject: JSONObject) {
                        if (jsonObject.has("success") && jsonObject.getBoolean("success") && jsonObject.getString(
                                "successMessage"
                            ).toString().contains("successfully")
                        ) {
                            showDialog()
                        } else {
                            makeToast("Something went wrong.")
                        }
                    }

                    override fun onError(volleyError: VolleyError) {
                        makeToast("Something went wrong.")
                    }

                })
            }
        }
    }

    private fun showDialog() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Success")
        alertDialog.setMessage("Password changed successfully")
        alertDialog.setCancelable(false)
        alertDialog.setPositiveButton("Login") { text, listener ->
            val intent = Intent(this@ResetPasswordActivity, SignInActivity::class.java)
            startActivity(intent)
        }
    }

    private fun checkFields(): Boolean {
        var result = false

        if (otpEt.text.toString() == "")
            otpEt.error = "Required field"
        else if (passwordEt.text.toString() == "")
            passwordEt.error = "Required field"
        else if (confirmPasswordEt.text.toString() == "")
            confirmPasswordEt.error = "Required field"
        else if (otpEt.text.toString().length < 4)
            makeToast("OTP must have 4 characters")
        else if (passwordEt.text.toString() != confirmPasswordEt.text.toString())
            makeToast("Passwords doesn't match")
        else
            result = true
        return result
    }

    private fun makeToast(str: String) {
        Toast.makeText(this@ResetPasswordActivity, str, Toast.LENGTH_SHORT).show()
    }
}