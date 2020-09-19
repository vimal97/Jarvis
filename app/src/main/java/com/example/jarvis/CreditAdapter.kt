package com.example.jarvis

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jarvis.ui.credits.CreditFragment
import com.google.gson.Gson
import kotlinx.android.synthetic.main.credit_container.view.*
import java.lang.Exception


class CreditAdapter(private val creditList: List<CreditData>, private val parentActivityView: View) : RecyclerView.Adapter<CreditAdapter.CreditViewHolder>() {

    private lateinit var holderUpdate: CreditData

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreditViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.credit_container, parent, false)
        return CreditViewHolder(itemView)
    }

    private fun openDialog(context: Context, data: CreditData){
        var  builder: AlertDialog.Builder = AlertDialog.Builder(context)
        var sharedPreference: SharedPreference
        val view = LayoutInflater.from(context).inflate(R.layout.viewcreditdata, null, false)
        var holderUpdate: CreditData = data
        view.findViewById<TextView>(R.id.text_name_credit_value).text = data.name
        view.findViewById<TextView>(R.id.text_amount_credit_value).text = data.amount
        view.findViewById<TextView>(R.id.text_borrow_credit_value).text = data.borrowed_date
        view.findViewById<TextView>(R.id.text_return_credit_value).text = data.return_date
        if(data.reason.length > 10){
            view.findViewById<TextView>(R.id.text_reason_credit_value).text = data.reason.subSequence(0,9).toString() + "..."
        }
        else{
            view.findViewById<TextView>(R.id.text_reason_credit_value).text = data.reason
        }

        view.findViewById<TextView>(R.id.text_reason_credit_value).setOnClickListener {
            view ->
            Toast.makeText(view.context,data.reason,Toast.LENGTH_SHORT).show()
        }

        builder.setView(view)
            .setTitle("Credit Information")
            .setNegativeButton("Cancel", DialogInterface.OnClickListener(){
                    dialigInterface: DialogInterface, i : Int ->

            })
            .setPositiveButton("Update", DialogInterface.OnClickListener(){
                    _: DialogInterface, _: Int ->

                var gson = Gson()
                val fetchedCreditDataConverted = mutableListOf<CreditData>()
                var sharedPreference = SharedPreference(context)
                var fetchedCreditData = sharedPreference.getCreditData("CreditList")
                var fetchedCreditDataList = fetchedCreditData?.split("|")?.toList()?.toMutableList()

                if(view.findViewById<CheckBox>(R.id.paidfull_credit).isChecked){
                    //remove the entry
                    var fetchedCreditDataListIterator: CreditData
                    if (fetchedCreditDataList != null) {
                        Log.v("Test_Vimal", "trying to remove")
                        for (j in fetchedCreditDataList) {
                            val i = fetchedCreditDataList.indexOf(j)
                            try {
                                if(fetchedCreditDataList[i] != ""){
                                    fetchedCreditDataListIterator = gson.fromJson(fetchedCreditDataList[i],CreditData::class.java)

                                    if(fetchedCreditDataListIterator.id == data.id){
                                        fetchedCreditDataList.removeAt(i)
                                        sharedPreference.pushCreditData("CreditList",fetchedCreditDataList.joinToString("|"))
                                    }
                                }
                            }
                            catch (e: Exception){
                                Log.v("Test_Vimal", e.toString())
                            }
                            }
                        }
                    }
                else{
                    //just update the value
                    Log.v("Test_Vimal", "trying to update")
                    var fetchedCreditDataListIterator: CreditData
                    if (fetchedCreditDataList != null) {
                        for( i in 1 until fetchedCreditDataList.size - 1){
                            if(fetchedCreditDataList[i] != ""){
                                fetchedCreditDataListIterator = gson.fromJson(fetchedCreditDataList[i],CreditData::class.java)
                                if(fetchedCreditDataListIterator.id == data.id){
                                    fetchedCreditDataListIterator.amount = view.findViewById<TextView>(R.id.text_amount_credit_value).text.toString()
                                    holderUpdate = fetchedCreditDataListIterator
                                    fetchedCreditDataList?.set(i,
                                        gson.toJson(fetchedCreditDataListIterator)
                                    )
                                }
                            }
                        }
                        sharedPreference.pushCreditData("CreditList",fetchedCreditDataList.joinToString("|"))
                    }
                }
                val recyclerView = parentActivityView.findViewById<RecyclerView>(R.id.recycler_viewer_credits)
                for(i in fetchedCreditDataList!!){
                    fetchedCreditDataConverted += gson.fromJson(i, CreditData::class.java)
                }
                Log.v("Test_Vimal", fetchedCreditDataConverted.toString())
                recyclerView.adapter = CreditAdapter(fetchedCreditDataConverted, parentActivityView)
                recyclerView.layoutManager = LinearLayoutManager(context)
                recyclerView.setHasFixedSize(true)
            })
        builder.create().show()
    }

    override fun getItemCount() = creditList.size

    override fun onBindViewHolder(holder: CreditViewHolder, position: Int) {
        Log.v("Test_Vimal", "Holder : " + holder.amount.text)
        var sharedPreference = SharedPreference(holder.itemView.context)
        var gson = Gson()
        var fetchedData = sharedPreference.getCreditData("CreditList")
        var fetchedDataList = fetchedData?.split("|")
        var temp: CreditData
        var tempList = listOf<String>()
        if (fetchedDataList != null) {
            for(i in fetchedDataList.indices){
                if(fetchedDataList[i] != ""){
                    tempList += fetchedDataList[i]
                }
            }
        }
        fetchedDataList = tempList

        if (fetchedDataList != null) {
            for ( i in fetchedDataList.indices){
                temp = gson.fromJson(fetchedDataList[i],CreditData::class.java)
                if(temp.id == creditList[position].id){
                    holder.name.text = temp.name
                    holder.amount.text = "\u20B9 " + temp.amount
                    holder.dueDate.text = "Due date : " + temp.return_date
                    holder.id = temp.id

                }
            }
        }
        else
            Log.v("Test_Vimal","Null is here : " + fetchedDataList.toString())

        holder.itemView.setOnClickListener {
            view ->

            for ( i in creditList){
                if(i.id == holder.id){
                    openDialog(view.context,i)
                }
            }
        }
    }

    class CreditViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val name : TextView = itemView.credit_name_1
        val amount : TextView = itemView.credit_value_1
        val dueDate : TextView = itemView.credit_dueDate
        var id: String = ""
    }
}