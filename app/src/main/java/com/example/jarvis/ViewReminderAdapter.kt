package com.example.jarvis

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.reminder_container.view.*

class ViewReminderAdapter(private val reminderList: List<ReminderData>): RecyclerView.Adapter<ViewReminderAdapter.ReminderViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.reminder_container, parent, false)
        return ReminderViewHolder(itemView)
    }

    override fun getItemCount() = reminderList.size

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        holder.task.text = reminderList[position].task
        holder.time.text = reminderList[position].time
    }

    class ReminderViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var task : TextView = itemView.viewTask
        val time : TextView = itemView.viewTime
    }

}