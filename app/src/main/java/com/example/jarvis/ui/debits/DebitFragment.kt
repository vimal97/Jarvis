@file:Suppress("DEPRECATION")

package com.example.jarvis.ui.debits

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jarvis.DebitAdapter
import com.example.jarvis.DebitData
import com.example.jarvis.R
import com.example.jarvis.SharedPreference
import com.google.gson.Gson

@Suppress("DEPRECATION")
class DebitFragment : Fragment() {

    private lateinit var debitViewModel: DebitViewModel
    private lateinit var sharedPreference: SharedPreference


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
        var gson: Gson = Gson()
        sharedPreference = this.context?.let { SharedPreference(it) }!!
        sharedPreference?.getDebitData("DebitList")?.split("|")?.toList()
        sharedPreference?.getDebitData("DebitList")?.split("|")?.toList()?.forEach { x ->
            if(x == "") {
                // do nothing
            }
            else {
                debitList += gson.fromJson(x,DebitData::class.java)
            }
        }
        recyclerView.adapter = DebitAdapter(debitList)
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.setHasFixedSize(true)
        return view
    }
}
