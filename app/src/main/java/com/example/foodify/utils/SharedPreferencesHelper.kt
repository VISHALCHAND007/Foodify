package com.example.foodify.utils

import android.annotation.SuppressLint
import android.content.Context
import com.example.foodify.R

class SharedPreferencesHelper {

    private lateinit var sharedPreferences: android.content.SharedPreferences

    @SuppressLint("CommitPrefEdits")
    fun getVariableInPreferences(key: String, mContext: Context): String {
        sharedPreferences = mContext.getSharedPreferences(
            mContext.getString(R.string.preferences_file_name),
            Context.MODE_PRIVATE
        )
        val value = sharedPreferences.getString(key, null)

        return value ?: ""
    }

    fun setVariableInPreferences(key: String, value: String, mContext: Context) {
        sharedPreferences = mContext.getSharedPreferences(
            mContext.getString(R.string.preferences_file_name),
            Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getBooleanInPreferences(key: String, mContext: Context): Boolean {
        sharedPreferences = mContext.getSharedPreferences(
            mContext.getString(R.string.preferences_file_name),
            Context.MODE_PRIVATE
        )

        return sharedPreferences.getBoolean(key, false)
    }

    fun setBooleanInPreferences(key: String, value: Boolean, mContext: Context) {
        sharedPreferences = mContext.getSharedPreferences(
            mContext.getString(R.string.preferences_file_name),
            Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }
}