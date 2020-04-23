package com.example.jarvis

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.expense_container.view.*

class ExpenseAdapter(private val expenseList: List<ExpenseData>): RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        holder.type.text = expenseList[position].type
        holder.amount.text = "\u20B9 " + expenseList[position].amount
    }

    override fun getItemCount(): Int = expenseList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseAdapter.ExpenseViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.expense_container, parent, false)
        return ExpenseAdapter.ExpenseViewHolder(itemView)
    }

    class ExpenseViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var type : TextView = itemView.expenseType
        val amount : TextView = itemView.expenseAmount
    }
}