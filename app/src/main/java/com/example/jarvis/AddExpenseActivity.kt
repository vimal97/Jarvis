package com.example.jarvis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_add_expense.*
import java.util.*

class AddExpenseActivity : AppCompatActivity() {

    private lateinit var expenseType: String
    private var newExpenseTypeFlag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)
        supportActionBar?.title = "Add Expense"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val expenseTypes = resources.getStringArray(R.array.expense_types)
        val spinner = findViewById<Spinner>(R.id.expenseTypes)
        if (spinner != null) {
            spinner.adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, expenseTypes)
            spinner.prompt = "Sample"
            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    // onclick doesn't trigger anything
                    if(expenseTypes[position] == "Others"){
                        findViewById<EditText>(R.id.newExpenseType).visibility = View.VISIBLE
                        newExpenseTypeFlag = true
                    }
                    else{
                        findViewById<EditText>(R.id.newExpenseType).visibility = View.GONE
                        expenseType = expenseTypes[position]
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }
    }

    fun addExpense(view: View){
        val amount = findViewById<EditText>(R.id.expense_amount).text.toString()
        //expense type is already received in expenseType
        if(newExpenseTypeFlag){
            expenseType = findViewById<EditText>(R.id.newExpenseType).text.toString()
        }
        val calendar = Calendar.getInstance();
        val today = calendar.get(Calendar.DAY_OF_MONTH).toString() + "/" + calendar.get(Calendar.MONTH).toString() + "/" + calendar.get(Calendar.YEAR).toString()
        val expenseData = ExpenseData(amount,today,expenseType)
        var gson = Gson()
        val sharedPreference = SharedPreference(this)
        val expenseDataFetched = sharedPreference.getExpenseData("ExpenseList_$today")
        var expenseDataList = expenseDataFetched?.split("|")?.toList()
        if (expenseDataList != null) {
            expenseDataList += gson.toJson(expenseData)
            sharedPreference.pushExpenseData("ExpenseList_$today",expenseDataList.joinToString("|"))
        }
        else{
            sharedPreference.pushExpenseData("ExpenseList_$today",gson.toJson(expenseData))
        }
        Log.v("Test_Vimal","Submitted expense data : " + sharedPreference.getExpenseData("ExpenseList_$today"))
        Toast.makeText(this@AddExpenseActivity, "Expense of $amount \u20B9 added.",Toast.LENGTH_SHORT).show()
        startActivity(Intent(this@AddExpenseActivity,dashboard::class.java))
    }

    fun uploadImage(view: View) {
        Toast.makeText(this@AddExpenseActivity,"Work in progress buddy, Stay tuned",Toast.LENGTH_SHORT).show()
    }
}
