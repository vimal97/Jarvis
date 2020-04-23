@file:Suppress("DEPRECATION")

package com.example.jarvis.ui.expenses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.jarvis.ExpenseData
import com.example.jarvis.R
import com.example.jarvis.SharedPreference
import com.google.gson.Gson
import java.util.*

@Suppress("DEPRECATION")
class ExpensesFragment : Fragment() {

    private lateinit var expensesViewModel: ExpensesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        expensesViewModel =
            ViewModelProviders.of(this).get(ExpensesViewModel::class.java)
        var view1 = inflater.inflate(R.layout.fragment_expenses, container, false)

        //show expense details
        val sharedPreference = SharedPreference(view1.context)
        val calendar = Calendar.getInstance()
        val today = calendar.get(Calendar.DAY_OF_MONTH).toString() + "/" + calendar.get(Calendar.MONTH).toString() + "/" + calendar.get(
            Calendar.YEAR).toString()
        val expenseDataFetched = sharedPreference.getExpenseData("ExpenseList_$today")
        var todaysExpense = 0
        if (expenseDataFetched != null) {
            var temp: ExpenseData
            val gson = Gson()
            for(i in expenseDataFetched.split("|").toList()){
                temp = gson.fromJson(i, ExpenseData::class.java)
                todaysExpense += temp.amount.toInt()
            }
            view1.findViewById<TextView>(R.id.todaysExpense).text = "\u20B9 ${todaysExpense.toString()}"
        }

        return view1
    }
}
