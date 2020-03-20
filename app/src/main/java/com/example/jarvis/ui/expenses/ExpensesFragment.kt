@file:Suppress("DEPRECATION")

package com.example.jarvis.ui.expenses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.jarvis.R

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
        return inflater.inflate(R.layout.fragment_expenses, container, false)
    }
}
