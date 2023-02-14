package com.example.foodify.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.example.foodify.R

class OrderHistoryFragment : Fragment() {
    private lateinit var progressRl: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_order_history, container, false)
        init(view)
        return view
    }
    private fun init(view: View) {
        initElements(view)
        initTasks()
        initListeners()
    }

    private fun initElements(view: View) {
        progressRl = view.findViewById(R.id.progressRl)
    }

    private fun initTasks() {

    }

    private fun initListeners() {

    }
}