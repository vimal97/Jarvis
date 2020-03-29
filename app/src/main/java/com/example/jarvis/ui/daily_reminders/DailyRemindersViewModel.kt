package com.example.jarvis.ui.daily_reminders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DailyRemindersViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is credit Fragment"
    }
    val text: LiveData<String> = _text
}