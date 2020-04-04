package com.example.jarvis

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.credit_container.view.*
import kotlinx.android.synthetic.main.debit_container.view.*

class CreditAdapter(private val creditList: List<CreditData>) : RecyclerView.Adapter<CreditAdapter.CreditViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreditViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.credit_container, parent, false)
        return CreditViewHolder(itemView)
    }

    override fun getItemCount() = creditList.size

    override fun onBindViewHolder(holder: CreditViewHolder, position: Int) {
        val currentItem = creditList[position]
        holder.name.text = currentItem.name
        holder.amount.text = currentItem.amount.toString()
        holder.dueDate.text = currentItem.return_date

    }

    class CreditViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val name : TextView = itemView.credit_name_1
        val amount : TextView = itemView.credit_value_1
        val dueDate : TextView = itemView.credit_dueDate
    }
}