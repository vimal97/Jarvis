package com.example.jarvis

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.ComponentCallbacks2
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import java.util.*
import com.example.jarvis.SharedPreference
import kotlin.collections.ArrayList


class AddDebitActivity : AppCompatActivity() {

    private lateinit var dateView: Button
    private lateinit var calendar: Calendar
    private lateinit var datePicker: DatePicker
    private var year: Int = 0
    private var month: Int = 0
    private var day: Int = 0
    private lateinit var expectedDate: String
    private lateinit var today: String
    val sharedPreference:SharedPreference=SharedPreference(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_debit)
        supportActionBar?.title = "Add Debit"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //setting DatePicker Widget
        dateView = findViewById<Button>(R.id.choose_date_debit);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        today = "$day/$month/$year"
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
        DatePickerDialog.OnDateSetListener { arg0, arg1, arg2, arg3 ->
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day
            showDate(arg1, arg2 + 1, arg3)
        }

    fun setDate(view: View?) {
        showDialog(999)
    }

    public fun addDebit(view: View){
        var amount = findViewById<EditText>(R.id.debit_amount).text.toString()
        var name = findViewById<EditText>(R.id.debit_name).text.toString()
        var reason = findViewById<EditText>(R.id.debit_reason).text.toString()

        //read json and append new debit data
        var debitList = sharedPreference.getDebitData("DebitList")
        var gson = Gson()
        if(debitList == null){
            var debitListArray = "".split("|").toList()
            var id = debitListArray.size + 1
            var debitData = DebitData(id.toString(),name,amount,today,expectedDate,reason)
            debitListArray += gson.toJson(debitData)
            sharedPreference.pushDebitData("DebitList",debitListArray.joinToString("|"))
        }
        else{
            var debitListArray = debitList.split("|").toList()
            var id = debitListArray.size + 1
            var debitData = DebitData(id.toString(),name,amount,today,expectedDate,reason)
            debitListArray += gson.toJson(debitData)
            sharedPreference.pushDebitData("DebitList",debitListArray.joinToString("|"))
        }
        Toast.makeText(view.context,"Successfully added a debit",Toast.LENGTH_SHORT).show()
        startActivity(Intent(this@AddDebitActivity,dashboard::class.java))
    }

    private fun showDate(year: Int, month: Int, day: Int) {
        expectedDate = StringBuilder().append(day).append("/").append(month).append("/").append(year).toString()
        dateView.text = StringBuilder().append("Chosen Date : ").append(expectedDate)
    }
}
