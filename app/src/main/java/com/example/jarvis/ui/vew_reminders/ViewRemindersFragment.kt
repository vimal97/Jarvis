package com.example.jarvis.ui.vew_reminders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        return inflater.inflate(R.layout.fragment_view_reminders, container, false)
    }
}