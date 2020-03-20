@file:Suppress("DEPRECATION")

package com.example.jarvis.ui.analysis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.jarvis.R

@Suppress("DEPRECATION")
class AnalysisFragment : Fragment() {

    private lateinit var analysisViewModel: AnalysisViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        analysisViewModel =
            ViewModelProviders.of(this).get(AnalysisViewModel::class.java)
        return inflater.inflate(R.layout.fragment_analysis, container, false)
    }
}