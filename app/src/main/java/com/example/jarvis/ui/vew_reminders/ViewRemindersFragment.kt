package com.example.jarvis.ui.vew_reminders

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jarvis.*
import com.google.gson.Gson
import java.util.Collections.list

class ViewRemindersFragment : Fragment() {

    private lateinit var viewRemindersViewModel: ViewRemindersViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewRemindersViewModel =
            ViewModelProviders.of(this).get(ViewRemindersViewModel::class.java)

        //adding element to view reminders
        val view = inflater.inflate(R.layout.fragment_view_reminders, container, false)
        val view1 = view
        val reminderTypes = resources.getStringArray(R.array.reminder_types)
        val sharedPreference = SharedPreference(view.context)
        val gson = Gson()
        val recyclerView = view1.findViewById<RecyclerView>(R.id.activeReminders)
        var reminderList = listOf<ReminderData>()
        val spinner = view?.findViewById<Spinner>(R.id.reminderType)
        if (spinner != null) {
            spinner.adapter = view?.context?.let {
                ArrayAdapter(
                    it,
                    R.layout.support_simple_spinner_dropdown_item,
                    reminderTypes
                )
            }
            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    // onclick doesn't trigger anything
                    val tempReminderData = ReminderData("","","")
                    var tempNReminderData: NormalReminderData
                    var tempDReminderData: DailyReminderData
                    if(reminderTypes[position] == "Normal"){
                        //filling the view with active normal reminders
                        Log.v("Test_Vimal","Choosen Normal Reminders")
                        val fetchedNormalReminders = sharedPreference.getNormalReminderData("NormalReminderList")
                        val normalRemindersList = fetchedNormalReminders?.split("|")?.toList()
                        if (normalRemindersList != null) {
                            for(i in normalRemindersList){
                                if(i != "" || i != null){
                                    tempNReminderData = gson.fromJson(i,NormalReminderData::class.java)
                                    tempReminderData.task = tempNReminderData.task
                                    tempReminderData.date = tempNReminderData.date
                                    tempReminderData.time = tempNReminderData.time
                                    reminderList += tempReminderData
                                }
                            }
                        }
                        reminderList = listOf(ReminderData("sample_normal","date","time"),ReminderData("sample1_normal","date1","time1"))
                        Log.v("Test_Vimal",reminderList.toString())
                        recyclerView.adapter = ViewReminderAdapter(reminderList)
                        recyclerView.layoutManager = LinearLayoutManager(view.context)
                        recyclerView.setHasFixedSize(true)
                    }
                    else if(reminderTypes[position] == "Recurring/Daily") {
                        //filling the view with recurring/daily reminder
                        Log.v("Test_Vimal","Choosen Recurring/Daily Reminders")
                        val fetchedDailyReminders = sharedPreference.getDailyReminderData("DailyReminderList")
                        val dailyRemindersList = fetchedDailyReminders?.split("|")?.toList()
                        if (dailyRemindersList != null) {
                            for(i in dailyRemindersList){
                                if(i != "" || i != null){
                                    tempDReminderData = gson.fromJson(i,DailyReminderData::class.java)
                                    tempReminderData.task = tempDReminderData.task
                                    tempReminderData.date = ""
                                    tempReminderData.time = tempDReminderData.timeToRemind
                                    reminderList += tempReminderData
                                }
                            }
                        }
                        reminderList = listOf(ReminderData("sample_daily","date","time"),ReminderData("sample1_daily","date1","time1"))
                        Log.v("Test_Vimal",reminderList.toString())
                        recyclerView.adapter = ViewReminderAdapter(reminderList)
                        recyclerView.layoutManager = LinearLayoutManager(view.context)
                        recyclerView.setHasFixedSize(true)
                    }
                    else{}
                    recyclerView.adapter?.notifyDataSetChanged()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }

        return view
    }
}