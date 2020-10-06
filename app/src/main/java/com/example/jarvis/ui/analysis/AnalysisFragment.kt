@file:Suppress("DEPRECATION")

package com.example.jarvis.ui.analysis

import CustomMarker
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.jarvis.ExpenseData
import com.example.jarvis.R
import com.example.jarvis.SharedPreference
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.gson.Gson
import lecho.lib.hellocharts.model.PieChartData
import lecho.lib.hellocharts.model.SliceValue
import lecho.lib.hellocharts.view.PieChartView
import java.util.*
import kotlin.collections.ArrayList


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

        var view = inflater.inflate(R.layout.fragment_analysis, container, false)
        val pieChartView: PieChartView = view.findViewById(R.id.analysisChart)

        var pieData: List<SliceValue> = ArrayList()
        val rnd = Random()

        //setting up the data to show in piechart
        val sharedPreference = SharedPreference(view.context)
        val gson = Gson()
        val calendar = Calendar.getInstance();
        val year = calendar.get(Calendar.YEAR);
        var month = calendar.get(Calendar.MONTH) + 1;
        val day = calendar.get(Calendar.DAY_OF_MONTH);
        var today = "$day/$month/$year"
        var temp = ""
        var finalDataKeys = listOf<String>()
        val finalDataValues = mutableListOf<Int>()
        var tempJson: ExpenseData
        var todaysTotal: Float
        var dayWiseExpense = listOf<Float>()
        var thisMonthExpense = mutableListOf<String>()
        thisMonthExpense.add("")
        for (i in 1..day){
            todaysTotal = 0F
            temp = sharedPreference.getExpenseData("ExpenseList_$i/$month/$year").toString()
            Log.v("Test_Vimal", "Data found on ExpenseList_$i/$month/$year : $temp")
            thisMonthExpense.add(temp)
            if(temp != "null"){
                Log.v("Test_Vimal","Value for pie ExpenseList_$i/$month/$year is : $temp")
                for(i in temp.split("|").toList()){
                    if(i != ""){
                        tempJson = gson.fromJson(i,ExpenseData::class.java)
                        todaysTotal += tempJson.amount.toFloat()
                        if(finalDataKeys.indexOf(tempJson.type) < 0){
                            if(tempJson.type in listOf<String>("Transport","Entertainment","Medical","Installments","Stationaries")){
                                finalDataKeys += tempJson.type
                                finalDataValues += tempJson.amount.toInt()
                            }
                            else{
                                if(finalDataKeys.indexOf("Others") < 0){
                                    finalDataKeys += "Others"
                                    finalDataValues += tempJson.amount.toInt()
                                }
                                else{
                                    finalDataValues[finalDataKeys.indexOf("Others")] += tempJson.amount.toInt()
                                }
                            }
                        }
                        else{
                            finalDataValues[finalDataKeys.indexOf(tempJson.type)] = finalDataValues[finalDataKeys.indexOf(tempJson.type)] + tempJson.amount.toInt()
                        }
                    }
                }
            }
            dayWiseExpense += todaysTotal
        }


        var color: Int

        for(i in finalDataKeys.indices){
            color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
            pieData += SliceValue(finalDataValues[i].toFloat(), color).setLabel(finalDataKeys[i] + " : \u20B9 " + finalDataValues[i])
        }

        val pieChartData = PieChartData(pieData)
        pieChartData.setHasLabels(true);
        pieChartData.setHasCenterCircle(true).setCenterText1("This month").setCenterText1FontSize(20)
            .centerText1Color = Color.parseColor("#0097A7");
        pieChartView.pieChartData = pieChartData

        //setting up line chart
        val entries = ArrayList<Entry>()
        for( i in dayWiseExpense.indices){
            entries.add(Entry(i.toFloat(), dayWiseExpense[i]))
        }
        val vl = LineDataSet(entries, "Days")
        vl.setDrawValues(false)
        vl.setDrawFilled(true)
        vl.lineWidth = 3f
        vl.fillColor = R.color.cardViewHeadline
        vl.fillAlpha = R.color.date_buttons_debit
        val lineChart = view.findViewById<com.github.mikephil.charting.charts.LineChart>(R.id.analysisLineChart)
        lineChart.xAxis.labelRotationAngle = 0f
        lineChart.data = LineData(vl)
        lineChart.axisRight.isEnabled = false
//        lineChart.xAxis.axisMaximum = j+0.1f
        lineChart.setTouchEnabled(true)
        lineChart.setPinchZoom(true)
        lineChart.description.text = "Days"
        lineChart.setNoDataText("No forex yet!")
        lineChart.animateX(1800, Easing.EaseInExpo)
        val markerView = CustomMarker(view.context, R.layout.marker_view)
        lineChart.marker = markerView

        //compare with previous month
        var thisMonthTransport = 0
        var thisMonthEntertainment = 0
        var thisMonthstationaries = 0
        var thisMonthOthers = 0
        var lastMonthTransport = 0
        var lastMonthEntertainment = 0
        var lastMonthstationaries = 0
        var lastMonthOthers = 0
        var lastMonthExpense = mutableListOf<String>()
        month -= 1
        lastMonthExpense.add("")
        for(i in 1..31){
            var tempList = mutableListOf<String>()
            lastMonthExpense.add(sharedPreference.getExpenseData("ExpenseList_$i/$month/$year").toString())
            if(i <= day){
                if(!(thisMonthExpense[i] == "null" || thisMonthExpense[i] == ""))
                {
                    Log.v("Test_Vimal", "This Month $i day : " + thisMonthExpense[i])
                    tempList = thisMonthExpense[i].split("|").toMutableList()
                    for(j in tempList){
                        if(j != "")
                        {
                            tempJson = gson.fromJson(j,ExpenseData::class.java)
                            when(tempJson.type){
                                "Transport" -> thisMonthTransport += tempJson.amount.toInt()
                                "Entertainment" -> thisMonthEntertainment += tempJson.amount.toInt()
                                "Stationaries" -> thisMonthstationaries += tempJson.amount.toInt()
                                "Installments" -> ""
                                "Medical" -> ""
                                else -> thisMonthOthers += tempJson.amount.toInt()
                            }
                        }
                    }
                }
            }
            if(!(lastMonthExpense[i] == "null" || lastMonthExpense[i] == "")){
                Log.v("Test_Vimal", "Last Month $i day : " + lastMonthExpense[i])
                tempList = lastMonthExpense[i].split("|").toMutableList()
                for(j in tempList){
                    if(j != ""){
                        tempJson = gson.fromJson(j,ExpenseData::class.java)
                        when(tempJson.type){
                            "Transport" -> lastMonthTransport += tempJson.amount.toInt()
                            "Entertainment" -> lastMonthEntertainment += tempJson.amount.toInt()
                            "Stationaries" -> lastMonthstationaries += tempJson.amount.toInt()
                            "Installments" -> ""
                            "Medical" -> ""
                            else -> lastMonthOthers += tempJson.amount.toInt()
                        }
                    }
                }
            }
        }
        Log.v("Test_Vimal", "This month transport : $thisMonthTransport")
        Log.v("Test_Vimal", "This month entertainment : $thisMonthEntertainment")
        Log.v("Test_Vimal", "This month stationaries : $thisMonthstationaries")
        Log.v("Test_Vimal", "This month Others : $thisMonthOthers")
        Log.v("Test_Vimal", "Last month transport : $lastMonthTransport")
        Log.v("Test_Vimal", "Last month entertainment : $lastMonthEntertainment")
        Log.v("Test_Vimal", "Last month stationaries : $lastMonthstationaries")
        Log.v("Test_Vimal", "Last month Others : $lastMonthOthers")
        var compareTransport = lastMonthTransport - thisMonthTransport
        if(compareTransport < 0){
            compareTransport *= -1
            (view.findViewById<TextView>(R.id.compare_transport_value)).setTextColor(resources.getColor(R.color.floating_close))
            (view.findViewById<TextView>(R.id.compare_transport_value)).text = "↑ $compareTransport ₹"
        }else{
            (view.findViewById<TextView>(R.id.compare_transport_value)).setTextColor(resources.getColor(R.color.submit_buttons))
            (view.findViewById<TextView>(R.id.compare_transport_value)).text = "↓ $compareTransport ₹"
        }
        var compareEntertainment = lastMonthEntertainment - thisMonthEntertainment
        if(compareEntertainment < 0){
            compareEntertainment *= -1
            (view.findViewById<TextView>(R.id.compare_entertainment_value)).setTextColor(resources.getColor(R.color.floating_close))
            (view.findViewById<TextView>(R.id.compare_entertainment_value)).text = "↑ $compareEntertainment ₹"
        }else{
            (view.findViewById<TextView>(R.id.compare_entertainment_value)).setTextColor(resources.getColor(R.color.submit_buttons))
            (view.findViewById<TextView>(R.id.compare_entertainment_value)).text = "↓ $compareEntertainment ₹"
        }
        var compareStationaries = lastMonthstationaries - thisMonthstationaries
        if(compareStationaries < 0){
            compareStationaries *= -1
            (view.findViewById<TextView>(R.id.compare_stationaries_value)).setTextColor(resources.getColor(R.color.floating_close))
            (view.findViewById<TextView>(R.id.compare_stationaries_value)).text = "↑ $compareStationaries ₹"
        }else{
            (view.findViewById<TextView>(R.id.compare_stationaries_value)).setTextColor(resources.getColor(R.color.submit_buttons))
            (view.findViewById<TextView>(R.id.compare_stationaries_value)).text = "↓ $compareStationaries ₹"
        }
        var compareOthers = lastMonthOthers - thisMonthOthers
        if(compareOthers < 0){
            compareOthers *= -1
            (view.findViewById<TextView>(R.id.compare_others_value)).setTextColor(resources.getColor(R.color.floating_close))
            (view.findViewById<TextView>(R.id.compare_others_value)).text = "↑ $compareOthers ₹"
        }else{
            (view.findViewById<TextView>(R.id.compare_others_value)).setTextColor(resources.getColor(R.color.submit_buttons))
            (view.findViewById<TextView>(R.id.compare_others_value)).text = "↓ $compareOthers ₹"
        }

        return view
    }
}