@file:Suppress("DEPRECATION")

package com.example.jarvis.ui.credits

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jarvis.CreditAdapter
import com.example.jarvis.CreditData
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
        val view: View = inflater.inflate(R.layout.fragment_credits, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_viewer_credits)
        val creditList = ArrayList<CreditData>()
        var item = CreditData("TestName",1000,"10/10/2020","20/10/2020","Nothing")
        creditList += item
        item = CreditData("TestName",1000,"10/10/2020","20/10/2020","Nothing")
        creditList += item
        item = CreditData("TestName",1000,"10/10/2020","20/10/2020","Nothing")
        creditList += item
        recyclerView.adapter = CreditAdapter(creditList)
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.setHasFixedSize(true)
        return view
    }
}
