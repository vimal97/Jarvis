package com.example.jarvis

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import java.util.*


@Suppress("DEPRECATION")
class AddCreditActivity : AppCompatActivity() {

    private lateinit var dateView: Button
    private lateinit var calendar: Calendar
    private lateinit var datePicker: DatePicker
    private var year: Int = 0
    private var month: Int = 0
    private var day: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_credit)
        supportActionBar?.title = "Add Credit"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //setting DatePicker Widget
        dateView = findViewById<Button>(R.id.choose_date_credit);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    override fun onCreateDialog(id: Int): Dialog? { // TODO Auto-generated method stub
        return if (id == 999) {
            DatePickerDialog(
                this,
                myDateListener, year, month, day
            )
        } else null
    }

    private val myDateListener =
        OnDateSetListener { arg0, arg1, arg2, arg3 ->
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day
            showDate(arg1, arg2 + 1, arg3)
        }

    fun setDate(view: View?) {
        showDialog(999)
    }

    private fun showDate(year: Int, month: Int, day: Int) {
        dateView.text = StringBuilder().append("Chosen Date : ").append(day).append("/").append(month).append("/").append(year)
    }
}
