package com.example.foodify.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.foodify.R
import com.example.foodify.utils.Constants
import com.example.foodify.utils.SharedPreferencesHelper
import de.hdodenhof.circleimageview.CircleImageView

class ProfileFragment : Fragment() {
    private lateinit var userImgIv: CircleImageView
    private lateinit var userNameTv: TextView
    private lateinit var userEmailTv: TextView
    private lateinit var userMobileTv: TextView
    private lateinit var userAddressTv: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        init(view)
        return view
    }

    private fun init(view: View) {
        initElements(view)
        initTasks()
        initListeners()
    }

    private fun initElements(view: View) {
        userImgIv = view.findViewById(R.id.userImg)
        userNameTv = view.findViewById(R.id.userName)
        userEmailTv = view.findViewById(R.id.email)
        userMobileTv = view.findViewById(R.id.phoneNumber)
        userAddressTv = view.findViewById(R.id.address)
    }

    private fun initTasks() {
        userNameTv.text = SharedPreferencesHelper().getVariableInPreferences(
            Constants().USER_NAME,
            requireContext()
        )
        userEmailTv.text = SharedPreferencesHelper().getVariableInPreferences(
            Constants().USER_EMAIL,
            requireContext()
        )
        userMobileTv.text = SharedPreferencesHelper().getVariableInPreferences(
            Constants().USER_NUMBER,
            requireContext()
        )
        userAddressTv.text = SharedPreferencesHelper().getVariableInPreferences(
            Constants().DELIVERY_ADDRESS,
            requireContext()
        )
        val userImage = SharedPreferencesHelper().getVariableInPreferences(
            Constants().USER_PHOTO,
            requireContext()
        )
        Glide.with(requireActivity()).load(userImage).error(R.drawable.default_pic).into(userImgIv)
    }

    private fun initListeners() {
        userImgIv.setOnClickListener {
            Toast.makeText(requireContext(), "Not supported for now !", Toast.LENGTH_SHORT).show()
        }
    }
}