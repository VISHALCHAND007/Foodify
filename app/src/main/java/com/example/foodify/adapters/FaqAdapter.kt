package com.example.foodify.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodify.R
import com.example.foodify.model.QuestionsModel

class FaqAdapter(private val mContext: Context, private val mList: ArrayList<QuestionsModel>) : RecyclerView.Adapter<FaqAdapter.FaqViewHolder>() {
    class FaqViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val questionTv: TextView = itemView.findViewById(R.id.questionTv)
        val answerTv: TextView = itemView.findViewById(R.id.answerTv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaqViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.sample_layout_faq, parent, false)
        return FaqViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: FaqViewHolder, position: Int) {
        val index = position + 1
        val question = "Q.$index "+mList[position].question
        val answer = "-> "+ mList[position].answer
        holder.questionTv.text = question
        holder.answerTv.text = answer
    }
}