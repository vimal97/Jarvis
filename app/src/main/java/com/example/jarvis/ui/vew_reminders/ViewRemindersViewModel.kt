package com.example.jarvis.ui.vew_reminders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewRemindersViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is view reminders Fragment"
    }
    val text: LiveData<String> = _text
}