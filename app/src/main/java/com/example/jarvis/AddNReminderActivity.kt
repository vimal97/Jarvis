package com.example.jarvis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class AddNReminderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_n_reminder)
        supportActionBar?.title = "Add Normal Reminder"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
