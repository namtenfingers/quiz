package com.example.quiz_api.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.quiz_api.OnItemClickListener
import com.example.quiz_api.R
import com.example.quiz_api.model.Caller

class CallAdapter(
    private val listCaller: ArrayList<Caller>,
    private val context: Context,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<CallAdapter.CallHolder>() {

    inner class CallHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivCaller: ImageView = itemView.findViewById(R.id.iv_caller)
        val tvCaller: TextView = itemView.findViewById(R.id.tv_caller)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(listCaller[adapterPosition])
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CallHolder
    = CallHolder(LayoutInflater.from(context).inflate(R.layout.item_call, parent, false))

    override fun getItemCount(): Int = listCaller.size

    override fun onBindViewHolder(holder: CallHolder, position: Int) {
        val caller = listCaller[position]
        holder.ivCaller.setImageResource(caller.image)
        holder.tvCaller.text = caller.name
    }

}