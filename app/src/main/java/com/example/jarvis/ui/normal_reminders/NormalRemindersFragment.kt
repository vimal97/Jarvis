package com.example.jarvis.ui.normal_reminders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.jarvis.R

class NormalRemindersFragment : Fragment() {

    private lateinit var normalRemindersViewModel: NormalRemindersViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        normalRemindersViewModel =
            ViewModelProviders.of(this).get(NormalRemindersViewModel::class.java)
        return inflater.inflate(R.layout.fragment_normal_reminders, container, false)
    }
}