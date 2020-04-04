@file:Suppress("DEPRECATION")

package com.example.jarvis.ui.debits

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jarvis.DebitAdapter
import com.example.jarvis.DebitData
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
        val view: View = inflater.inflate(R.layout.fragment_debits, container, false)
        val recyclerView: RecyclerView = view.findViewById<RecyclerView>(R.id.recycler_viewer_debits)
        val debitList = ArrayList<DebitData>()
        var item = DebitData("TestName",1000,"10/10/2020","20/10/2020","Nothing")
        debitList += item
        item = DebitData("TestName",1000,"10/10/2020","20/10/2020","Nothing")
        debitList += item
        item = DebitData("TestName",1000,"10/10/2020","20/10/2020","Nothing")
        debitList += item
        recyclerView.adapter = DebitAdapter(debitList)
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.setHasFixedSize(true)
        return view
    }
}
