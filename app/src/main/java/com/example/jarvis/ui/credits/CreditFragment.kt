@file:Suppress("DEPRECATION")

package com.example.jarvis.ui.credits

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.jarvis.R

class CreditFragment : Fragment() {

    private lateinit var creditViewModel: CreditViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        creditViewModel =
            ViewModelProviders.of(this).get(CreditViewModel::class.java)
        return inflater.inflate(R.layout.fragment_credits, container, false)
    }
}
