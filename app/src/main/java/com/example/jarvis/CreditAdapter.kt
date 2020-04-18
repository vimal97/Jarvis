package com.example.jarvis

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jarvis.ui.credits.CreditFragment
import com.google.gson.Gson
import kotlinx.android.synthetic.main.credit_container.view.*


class CreditAdapter(private val creditList: List<CreditData>) : RecyclerView.Adapter<CreditAdapter.CreditViewHolder>() {

    private lateinit var holderUpdate: CreditData

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreditViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.credit_container, parent, false)
        return CreditViewHolder(itemView)
    }

    public fun openDialog(context: Context, data: CreditData){
        var  builder: AlertDialog.Builder = AlertDialog.Builder(context)
        var sharedPreference: SharedPreference
        val view = LayoutInflater.from(context).inflate(R.layout.viewcreditdata, null, false)
        var holderUpdate: CreditData = data
        view.findViewById<TextView>(R.id.text_name_credit_value).text = data.name
        view.findViewById<TextView>(R.id.text_amount_credit_value).text = data.amount
        view.findViewById<TextView>(R.id.text_borrow_credit_value).text = data.borrowed_date
        view.findViewById<TextView>(R.id.text_return_credit_value).text = data.return_date
        view.findViewById<TextView>(R.id.text_reason_credit_value).text = data.reason

        builder.setView(view)
            .setTitle("Credit Information")
            .setNegativeButton("Cancel", DialogInterface.OnClickListener(){
                    dialigInterface: DialogInterface, i : Int ->

            })
            .setPositiveButton("Update", DialogInterface.OnClickListener(){
                dialogInterface: DialogInterface, i : Int ->
                var gson = Gson()
                var sharedPreference = SharedPreference(context)
                var fetchedCreditData = sharedPreference.getCreditData("CreditList")
                Log.v("Test_Vimal","Fetched data : " + fetchedCreditData.toString())
                var fetchedCreditDataList = fetchedCreditData?.split("|")?.toList()?.toMutableList()
                Log.v("Test_Vimal","Fetched data -> list converted : " + fetchedCreditDataList)
                var fetchedCreditDataListIterator: CreditData
                if (fetchedCreditDataList != null) {
                    for( i in 0 until fetchedCreditDataList.size){
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
                }
                Log.v("Test_Vimal","Fetched data -> after updation : " + fetchedCreditDataList)
                if (fetchedCreditDataList != null) {
                    sharedPreference.pushCreditData("CreditList",fetchedCreditDataList.joinToString("|"))
                }
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
            for(i in 0..fetchedDataList.size - 1){
                if(fetchedDataList[i] != ""){
                    tempList += fetchedDataList[i]
                }
            }
        }
        fetchedDataList = tempList

        if (fetchedDataList != null) {
            for ( i in fetchedDataList.indices){
                temp = gson.fromJson(fetchedDataList[i],CreditData::class.java)
                Log.v("Test_Vimal","Temp value : " + temp.toString() + " and holder id is : " + holder.id)
                if(temp.id == creditList[position].id){
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

            for ( i in creditList){
                if(i.id == holder.id){
                    openDialog(view.context,i)
//                    holder.amount.text = "\u20B9 " +  holderUpdate.amount
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