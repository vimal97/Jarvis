package com.example.jarvis

import android.app.DatePickerDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import java.util.*

class SearchExpenseActivity : AppCompatActivity() {

    private lateinit var dateView: Button
    private lateinit var calendar: Calendar
    private lateinit var datePicker: DatePicker
    private var year: Int = 0
    private var month: Int = 0
    private var day: Int = 0
    private lateinit var expectedDate: String
    private lateinit var today: String

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_expense)
        supportActionBar?.title = "Search for expenses"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    fun chooseDate(view: View){
        showDialog(999)
    }

    private fun showDate(year: Int, month: Int, day: Int) {
        expectedDate = StringBuilder().append(day).append("/").append(month).append("/").append(year).toString()
        dateView = findViewById<Button>(R.id.searchDate)
        dateView.text = StringBuilder().append("Searching for ").append(expectedDate)

        val recyclerView = findViewById<RecyclerView>(R.id.searchExpenseRecyclerView)
        val sharedPreference = SharedPreference(this)
        findViewById<ConstraintLayout>(R.id.expenseNotFound).visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
        //obtain the date for searching and it is saved to today
        Log.v("Test_Vimal","Searching for ExpenseList_$expectedDate")
        val expenseDataFetched = sharedPreference.getExpenseData("ExpenseList_$expectedDate")
        var expenseList: List<ExpenseData> = listOf()
        val gson = Gson()
        if(expenseDataFetched != ""){
            Log.v("Test_Vimal", "Fetched Expense Data at View Todays Expense : $expenseDataFetched")
            if (expenseDataFetched != null) {
                for(i in expenseDataFetched.split("|").toList()){
                    if(i != ""){
                        Log.v("Test_Vimal", "Setting adapter list : $i")
                        expenseList += gson.fromJson(i,ExpenseData::class.java)
                    }
                }
            }
            else{
                //display data not found message
                recyclerView.visibility = View.GONE
                findViewById<ConstraintLayout>(R.id.expenseNotFound).visibility = View.VISIBLE
            }
        }

        recyclerView.adapter = ExpenseAdapter(expenseList)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.setHasFixedSize(true)
    }
}
