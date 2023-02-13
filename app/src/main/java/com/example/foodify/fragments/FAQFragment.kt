package com.example.foodify.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide.init
import com.example.foodify.R
import com.example.foodify.adapters.FaqAdapter
import com.example.foodify.model.QuestionsModel

class FAQFragment : Fragment() {
    private val questionList = arrayListOf(
        "How do I Claim a Free Coupon?",
        "How do I Make a regular Table Booking?",
        "How do I book a Bookable-Special?",
        "How can I be certain my booking's been received?",
        "What happens if I don't reconfirm my booking?"
    )

    private val answerList = arrayListOf(
        "Coupons are free to claim, use the search or find somewhere close by using the Google Map View.\n" +
                "\n" +
                "Once you've found a great coupon offer, click the day you wish to use it, fill out your details to complete.\n" +
                "\n" +
                "You'll instantly receive your free coupon to your email inbox \n" +
                "\n" +
                "To redeem - simply present coupon to staff when paying.",
        "It's a piece of cake - once you've found your preferred restaurant simply select your Date and Time required, and then so long as we have an email address and phone number your booking is instantly confirmed the minute you hit \"Book\"",
        "It's quick and easy - once you've found your preferred restaurant special, simply click 'Book Special' then select a date when its available, then the time, and then so long as we have an email address and phone number your booking is instantly confirmed the minute you hit \"Book\"",
        "We'll send an instant confirmation email to the address used in your booking. If you book more than a day ahead, we'll also send a reminder 24 hours before your booking is due. You can reconfirm your booking using a link in your reminder email.",
        "Don't worry, your restaurant will still hold your booking without a reconfirmation.",
    )
    private lateinit var mList: ArrayList<QuestionsModel>
    private lateinit var progressLayout: RelativeLayout
    private lateinit var questionsRv: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var faqAdapter: FaqAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_f_a_q, container, false)
        init(view)
        return view
    }

    private fun init(view: View) {
        initElements(view)
        initTasks()
        initListeners()
    }

    private fun initElements(view: View) {
        progressLayout = view.findViewById(R.id.progressRl)
        questionsRv = view.findViewById(R.id.questionsRv)
        mList = arrayListOf()
        linearLayoutManager = LinearLayoutManager(requireContext())
    }

    private fun initTasks() {
        for (i in 0 until questionList.size) {
            //get data
            val listItem = QuestionsModel(
                questionList[i],
                answerList[i]
            )
            mList.add(listItem)
        }


        Handler(Looper.myLooper()!!).postDelayed({
            progressLayout.visibility = View.GONE
            setData()
        }, 1000)
    }

    private fun initListeners() {

    }

    private fun setData() {
        faqAdapter = FaqAdapter(requireContext(), mList)
        questionsRv.layoutManager = linearLayoutManager
        questionsRv.adapter = faqAdapter
    }
}