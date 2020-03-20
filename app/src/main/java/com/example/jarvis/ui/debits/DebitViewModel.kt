package com.example.jarvis.ui.debits

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DebitViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is debit Fragment"
    }
    val text: LiveData<String> = _text
}