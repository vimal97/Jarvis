@file:Suppress("DEPRECATION")

package com.example.jarvis.ui.credits

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jarvis.*
import com.google.gson.Gson

class CreditFragment : Fragment() {

    private lateinit var creditViewModel: CreditViewModel
    private lateinit var sharedPreference: SharedPreference
    private var creditList = ArrayList<CreditData>()
    private lateinit var view1: View
    public lateinit var container: ViewGroup
    public lateinit var inflater: LayoutInflater

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        creditViewModel =
            ViewModelProviders.of(this).get(CreditViewModel::class.java)
        view1 = inflater.inflate(R.layout.fragment_credits, container, false)
        Log.v("Test_Vimal","Container : " + container.toString())
        Log.v("Test_Vimal","Inflater : " + inflater.toString())
        var recyclerView: RecyclerView
        recyclerView = view1.findViewById<RecyclerView>(R.id.recycler_viewer_credits)
        var gson = Gson()
        sharedPreference = this.context?.let { SharedPreference(it) }!!
        sharedPreference.getCreditData("CreditList")?.split("|")?.toList()?.forEach{
                x ->
            if(x == ""){
                //do nothing
            } else{
                this.creditList.plusAssign(gson.fromJson(x,CreditData::class.java))
            }
        }
        recyclerView.adapter = CreditAdapter(this.creditList)
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.setHasFixedSize(true)
        return view1
    }
}
