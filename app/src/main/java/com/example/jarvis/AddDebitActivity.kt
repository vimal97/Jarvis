package com.example.jarvis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class AddDebitActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_debit)
        supportActionBar?.title = "Add Debit"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
