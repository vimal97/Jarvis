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
import com.example.jarvis.SharedPreference
import com.google.gson.Gson

class CreditFragment : Fragment() {

    private lateinit var creditViewModel: CreditViewModel
    private lateinit var sharedPreference: SharedPreference

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
        var gson = Gson()
        sharedPreference = this.context?.let { SharedPreference(it) }!!
        sharedPreference.getCreditData("CreditList")?.split("|")?.toList()?.forEach{
                x ->
            if(x == ""){
                //do nothing
            } else{
                creditList += gson.fromJson(x,CreditData::class.java)
            }
        }
        recyclerView.adapter = CreditAdapter(creditList)
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.setHasFixedSize(true)
        return view
    }
}
