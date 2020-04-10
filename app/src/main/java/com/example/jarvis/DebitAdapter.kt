package com.example.jarvis

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.debit_container.view.*

class DebitAdapter(private val debitList: List<DebitData>) : RecyclerView.Adapter<DebitAdapter.DebitViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DebitViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.debit_container, parent, false)
        return DebitViewHolder(itemView)
    }

    override fun getItemCount() = debitList.size

    override fun onBindViewHolder(holder: DebitViewHolder, position: Int) {
        val currentItem = debitList[position]
        holder.name.text = currentItem.name
        holder.amount.text = "\u20B9" + currentItem.amount
        holder.dueDate.text = "Due date : " + currentItem.return_date

    }

    class DebitViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val name : TextView = itemView.debit_name_1
        val amount : TextView = itemView.debit_value_1
        val dueDate : TextView = itemView.debit_dueDate
    }
}