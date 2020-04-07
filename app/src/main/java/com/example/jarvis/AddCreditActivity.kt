package com.example.jarvis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

@Suppress("DEPRECATION")
class AddCreditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_credit)
        supportActionBar?.title = "Add Credit"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
