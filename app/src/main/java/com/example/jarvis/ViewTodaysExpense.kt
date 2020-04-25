package com.example.jarvis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import java.util.*
import kotlin.collections.ArrayList

class ViewTodaysExpense : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_todays_expense)
        supportActionBar?.title = "Today's Expense"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val recyclerView = findViewById<RecyclerView>(R.id.todaysExpenseRecyclerView)
        findViewById<ConstraintLayout>(R.id.expenseNotFoundToday).visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
        val sharedPreference = SharedPreference(this)
        val calendar = Calendar.getInstance()
        val today = calendar.get(Calendar.DAY_OF_MONTH).toString() + "/" + (calendar.get(Calendar.MONTH) + 1).toString() + "/" + calendar.get(
            Calendar.YEAR).toString()
        val expenseDataFetched = sharedPreference.getExpenseData("ExpenseList_$today")
        Log.v("Test_Vimal","Todays expense is stored in : ExpenseList_$today")
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
                Log.v("Test_Vimal","display data not found message")
                recyclerView.visibility = View.GONE
                findViewById<ConstraintLayout>(R.id.expenseNotFoundToday).visibility = View.VISIBLE
            }
        }

        recyclerView.adapter = ExpenseAdapter(expenseList)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.setHasFixedSize(true)
    }
}
