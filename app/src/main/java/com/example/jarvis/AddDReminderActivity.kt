package com.example.jarvis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class AddDReminderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_d_reminder)
        supportActionBar?.title = "Add Daily Reminder"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
