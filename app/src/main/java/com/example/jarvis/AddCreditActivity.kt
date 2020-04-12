package com.example.jarvis

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import com.example.jarvis.SharedPreference
import com.google.gson.Gson


@Suppress("DEPRECATION")
class AddCreditActivity : AppCompatActivity() {

    private lateinit var dateView: Button
    private lateinit var calendar: Calendar
    private lateinit var datePicker: DatePicker
    private var year: Int = 0
    private var month: Int = 0
    private var day: Int = 0
    private var sharedPreference: SharedPreference = SharedPreference(this)
    private lateinit var today: String
    private lateinit var expectedDate: String

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
        today = "$day/$month/$year"
        sharedPreference.removeData("CreditList")
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
        expectedDate = StringBuilder().append(day).append("/").append(month).append("/").append(year).toString()
        dateView.text = StringBuilder().append("Chosen Date : ").append(expectedDate)
    }


    fun addCredit(view: View) {
        var amount = findViewById<EditText>(R.id.credit_amount).text.toString()
        var name = findViewById<EditText>(R.id.credit_name).text.toString()
        var reason = findViewById<EditText>(R.id.credit_reason).text.toString()

        var gson = Gson()
        var creditList = sharedPreference.getDebitData("CreditList")
        if(creditList == null){
            var creditListArray = "".split("|").toList()
            var id: String = (creditListArray.size + 1).toString()
            var creditData = CreditData(id, name,amount,today,expectedDate,reason)
            creditListArray += gson.toJson(creditData)
            sharedPreference.pushCreditData("CreditList",creditListArray.joinToString("|"))
        }
        else{
            var creditListArray = creditList.split("|").toList()
            var id: String = (creditListArray.size + 1).toString()
            var creditData = CreditData(id, name,amount,today,expectedDate,reason)
            creditListArray += gson.toJson(creditData)
            sharedPreference.pushCreditData("CreditList",creditListArray.joinToString("|"))
        }
    }
}
