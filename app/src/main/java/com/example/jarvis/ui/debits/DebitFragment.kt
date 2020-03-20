@file:Suppress("DEPRECATION")

package com.example.jarvis.ui.debits

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.jarvis.R

@Suppress("DEPRECATION")
class DebitFragment : Fragment() {

    private lateinit var debitViewModel: DebitViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        debitViewModel =
            ViewModelProviders.of(this).get(DebitViewModel::class.java)
        return inflater.inflate(R.layout.fragment_debits, container, false)
    }
}
