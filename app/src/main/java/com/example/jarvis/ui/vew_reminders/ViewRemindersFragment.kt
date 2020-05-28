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
import com.example.jarvis.R

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
        val reminderTypes = resources.getStringArray(R.array.reminder_types)
        Log.v("Test_Vimal", reminderTypes.toList().toString())
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
                    if(reminderTypes[position] == "Normal"){
                        Log.v("Test_Vimal","Normal reminders")
                    }
                    else if(reminderTypes[position] == "Recurring/Daily") {
                        Log.v("Test_Vimal","Recurring/Daily")
                    }
                    else{}
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }

        return view
    }
}