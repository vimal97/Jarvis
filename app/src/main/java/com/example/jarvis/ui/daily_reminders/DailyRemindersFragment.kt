package com.example.jarvis.ui.daily_reminders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.jarvis.R

class DailyRemindersFragment : Fragment() {

    private lateinit var dailyRemindersViewModel: DailyRemindersViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dailyRemindersViewModel =
            ViewModelProviders.of(this).get(DailyRemindersViewModel::class.java)
        return inflater.inflate(R.layout.fragment_daily_reminder, container, false)
    }
}
