@file:Suppress("DEPRECATION")

package com.example.jarvis.ui.debits

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
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

    public fun openDialog(context: Context,data: DebitData){
        var  builder: AlertDialog.Builder = AlertDialog.Builder(context)
        val view = LayoutInflater.from(context).inflate(R.layout.viewdebitdata, null, false)
        view.findViewById<TextView>(R.id.text_name_debit_value).text = data.name
        view.findViewById<TextView>(R.id.text_amount_debit_value).text = data.amount
        view.findViewById<TextView>(R.id.text_borrow_debit_value).text = data.borrowed_date
        view.findViewById<TextView>(R.id.text_return_debit_value).text = data.return_date
        view.findViewById<TextView>(R.id.text_reason_debit_value).text = data.reason

        builder.setView(view)
            .setTitle("Debit Information")
            .setNegativeButton("Cancel", DialogInterface.OnClickListener(){
                    dialigInteface: DialogInterface,i : Int ->

            })
            .setPositiveButton("Update",DialogInterface.OnClickListener(){
                    dialogInterface: DialogInterface,i: Int ->

            })
        builder.create().show()
    }

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
