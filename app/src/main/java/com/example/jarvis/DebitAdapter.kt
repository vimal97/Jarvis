package com.example.jarvis

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.jarvis.ui.debits.DebitFragment
import com.google.gson.Gson
import kotlinx.android.synthetic.main.debit_container.view.*

class DebitAdapter(private val debitList: List<DebitData>) : RecyclerView.Adapter<DebitAdapter.DebitViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DebitViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.debit_container, parent, false)
        return DebitViewHolder(itemView)
    }

    private fun openDialog(context: Context, data: DebitData){
        var  builder: AlertDialog.Builder = AlertDialog.Builder(context)
        var sharedPreference: SharedPreference
        val view = LayoutInflater.from(context).inflate(R.layout.viewdebitdata, null, false)
        var holderUpdate: DebitData = data
        view.findViewById<TextView>(R.id.text_name_debit_value).text = data.name
        view.findViewById<TextView>(R.id.text_amount_debit_value).text = data.amount
        view.findViewById<TextView>(R.id.text_borrow_debit_value).text = data.borrowed_date
        view.findViewById<TextView>(R.id.text_return_debit_value).text = data.return_date
        if(data.reason.length > 10){
            view.findViewById<TextView>(R.id.text_reason_debit_value).text = data.reason.subSequence(0,9).toString() + "..."
        }
        else{
            view.findViewById<TextView>(R.id.text_reason_debit_value).text = data.reason
        }

        view.findViewById<TextView>(R.id.text_reason_debit_value).setOnClickListener {
            view ->
            Toast.makeText(view.context,data.reason,Toast.LENGTH_SHORT).show()
        }

        builder.setView(view)
            .setTitle("Debit Information")
            .setNegativeButton("Cancel", DialogInterface.OnClickListener(){
                    dialigInterface: DialogInterface, i : Int ->

            })
            .setPositiveButton("Update", DialogInterface.OnClickListener(){
                    dialogInterface: DialogInterface, i : Int ->
                var gson = Gson()
                var sharedPreference = SharedPreference(context)
                var fetchedDebitData = sharedPreference.getCreditData("DebitList")
                Log.v("Test_Vimal","Fetched data : " + fetchedDebitData.toString())
                var fetchedDebitDataList = fetchedDebitData?.split("|")?.toList()?.toMutableList()
                Log.v("Test_Vimal","Fetched data -> list converted : " + fetchedDebitDataList)
                if(view.findViewById<CheckBox>(R.id.paidfull_debit).isChecked){
                    //remove the entry
                    var fetchedDebitDataListIterator: DebitData
                    if (fetchedDebitDataList != null) {
                        for( i in 0 until fetchedDebitDataList.size){
                            if(fetchedDebitDataList[i] != ""){
                                fetchedDebitDataListIterator = gson.fromJson(fetchedDebitDataList[i],DebitData::class.java)
                                if(fetchedDebitDataListIterator.id == data.id){
                                    fetchedDebitDataList.removeAt(i)
                                    sharedPreference.pushCreditData("DebitList",fetchedDebitDataList.joinToString("|"))
                                }
                            }
                        }
                    }
                }
                else{
                    //update the value
                    var fetchedDebitDataListIterator: DebitData
                    if (fetchedDebitDataList != null) {
                        for( i in 0 until fetchedDebitDataList.size){
                            if(fetchedDebitDataList[i] != ""){
                                fetchedDebitDataListIterator = gson.fromJson(fetchedDebitDataList[i],DebitData::class.java)
                                if(fetchedDebitDataListIterator.id == data.id){
                                    fetchedDebitDataListIterator.amount = view.findViewById<TextView>(R.id.text_amount_debit_value).text.toString()
                                    holderUpdate = fetchedDebitDataListIterator
                                    fetchedDebitDataList?.set(i,
                                        gson.toJson(fetchedDebitDataListIterator)
                                    )
                                }
                            }
                        }
                        sharedPreference.pushCreditData("DebitList",fetchedDebitDataList.joinToString("|"))
                        Log.v("Test_Vimal","Fetched data -> after updation : " + fetchedDebitDataList)
                    }
                }
            })
        builder.create().show()
    }

    override fun getItemCount() = debitList.size

    override fun onBindViewHolder(holder: DebitViewHolder, position: Int) {
        Log.v("Test_Vimal", "Holder : " + holder.amount.text)
        var sharedPreference = SharedPreference(holder.itemView.context)
        var gson = Gson()
        var fetchedData = sharedPreference.getDebitData("DebitList")
        var fetchedDataList = fetchedData?.split("|")
        var temp: DebitData
        var tempList = listOf<String>()
        if (fetchedDataList != null) {
            for(i in 0..fetchedDataList.size - 1){
                if(fetchedDataList[i] != ""){
                    tempList += fetchedDataList[i]
                }
            }
        }
        fetchedDataList = tempList

        if (fetchedDataList != null) {
            for ( i in fetchedDataList.indices){
                temp = gson.fromJson(fetchedDataList[i],DebitData::class.java)
                Log.v("Test_Vimal","Temp value : " + temp.toString() + " and holder id is : " + holder.id)
                if(temp.id == debitList[position].id){
                    Log.v("Test_Vimal","Identified data is : " + temp)
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

            for ( i in debitList){
                if(i.id == holder.id){
                    openDialog(view.context,i)
//                    holder.amount.text = "\u20B9 " +  holderUpdate.amount
                }
            }
        }
    }

    class DebitViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val name : TextView = itemView.debit_name_1
        val amount : TextView = itemView.debit_value_1
        val dueDate : TextView = itemView.debit_dueDate
        var id: String = ""
    }
}