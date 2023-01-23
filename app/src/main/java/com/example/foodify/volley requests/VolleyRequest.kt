package com.example.foodify.volley

import android.app.DownloadManager
import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.lang.Exception

class VolleyRequest {
    lateinit var listener: VolleyRequestListener

    fun VolleyRequest() {

    }

    interface VolleyRequestListener {
        public fun onDataLoaded(jsonObject: JSONObject)
        public fun onError()
    }



    public fun setVolleyRequestlistener(listener: VolleyRequestListener) {
        this.listener = listener
    }

    public fun makeGetRequest(url: String, parameter: Map<String, String>, context: Context) {
        val queue = Volley.newRequestQueue(context)
        val jsonObjectRequest = object:JsonObjectRequest(Request.Method.GET, url, null, Response.Listener {
            try {
                listener.onDataLoaded(it)
            } catch (e: Exception) {

            }
        }, Response.ErrorListener {

        }) {
            override fun getHeaders(): MutableMap<String, String> {
                val header = HashMap<String, String>()
                header["Content-type"] = "application/json"
                header["token"] = "17568148669265"
                return header
            }
        }
        queue.add(jsonObjectRequest)
    }
    public fun makePostRequest(url: String, jsonObject: JSONObject, context: Context) {
        val queue = Volley.newRequestQueue(context)
        val jsonObjectRequest = object:JsonObjectRequest(Method.POST, url, jsonObject, Response.Listener {
            try {
                listener.onError()
            } catch (e: Exception) {

            }
        }, Response.ErrorListener {

        }) {
            override fun getHeaders(): MutableMap<String, String> {
                val header = HashMap<String, String>()
                header["Content-type"] = "application/json"
                header["token"] = "17568148669265"
                return header
            }
        }
    }
}