package com.example.jarvis

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
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
        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context, reminderList[position].task, Toast.LENGTH_SHORT).show()
        }
        holder.itemView.findViewById<ImageView>(R.id.reminderClose).setOnClickListener {
            Toast.makeText(holder.itemView.context, "Removing " + reminderList[position].task,Toast.LENGTH_SHORT).show()
            Log.v("Test_Vimal", "Before removal : $reminderList")
            val reminderListMutable = reminderList.toMutableList()
            for(i in reminderListMutable){
                if(i.task == reminderListMutable[position].task && i.time == reminderListMutable[position].time){
                    reminderListMutable.removeAt(reminderListMutable.indexOf(i))
                }
            }
            Log.v("Test_Vimal", "After removal : $reminderListMutable")
            var sharedPreference = SharedPreference(holder.itemView.context)
            var gson = Gson()
            var outputList: List<String> = listOf()
            for(i in reminderListMutable){
                outputList += gson.toJson(i)
            }
            if(reminderList[position].type == "daily"){
                sharedPreference.pushDailyReminderData("DailyReminderList", outputList.joinToString("|"))
            }
            else if(reminderList[position].type == "normal"){
                sharedPreference.pushDailyReminderData("NormalReminderList", outputList.joinToString("|"))
            }
        }
    }

    class ReminderViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var task : TextView = itemView.viewTask
        val time : TextView = itemView.viewTime
    }

}