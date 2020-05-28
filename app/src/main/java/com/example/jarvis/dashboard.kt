package com.example.jarvis


import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.text.style.UpdateAppearance
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.net.toFile
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.jarvis.ui.DailyNotificationReceiver
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import org.json.JSONObject
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.nio.channels.FileChannel
import java.text.SimpleDateFormat
import java.util.*


@Suppress("DEPRECATION")
class dashboard : AppCompatActivity() {

    private lateinit var fab: FloatingActionButton
    private lateinit var fab1: LinearLayout
    private lateinit var fab2: LinearLayout
    private lateinit var fab3: LinearLayout
    private lateinit var fab4: LinearLayout
    private lateinit var fab5: LinearLayout
    private var isFABOpen: Boolean = false
    private var year: Int = 0
    private var month: Int = 0
    private var day: Int = 0
    private var choosen_mon = false
    private var choosen_tue = false
    private var choosen_wed = false
    private var choosen_thu = false
    private var choosen_fri = false
    private var choosen_sat = false
    private var choosen_sun = false
    private var dailyReminderDays = mutableListOf<String>()
    private var dailyReminderTime = ""
    private var dailyReminderHour = 0
    private var dailyReminderMinute = 0
    private var normalReminderDate = ""
    private var normalReminderTime = ""
    private lateinit var calendar: Calendar
    private var today = ""
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        fab1 = findViewById<LinearLayout>(R.id.add_credit_menu) //add expense
        fab2 = findViewById<LinearLayout>(R.id.add_credit_menu_1) //add credit
        fab3 = findViewById<LinearLayout>(R.id.add_credit_menu_2) //add debit
        fab4 = findViewById<LinearLayout>(R.id.add_credit_menu_3) //add normal reminder
        fab5 = findViewById<LinearLayout>(R.id.add_credit_menu_4) //add daily reminder
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        fab = findViewById<FloatingActionButton>(R.id.fab_main)
        fab.setOnClickListener { view ->
            if (!isFABOpen) {
                showFABMenu()
            } else {
                closeFABMenu()
            }
            //            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            //                .setAction("Action", null).show()
        }
        fab1.setOnClickListener { view ->
            //redirecting to add expense activity
            startActivity(Intent(this@dashboard, AddExpenseActivity::class.java))
        }
        fab2.setOnClickListener { view ->
            //redirecting to add credit activity
            startActivity(Intent(this@dashboard, AddCreditActivity::class.java))
        }
        fab3.setOnClickListener { view ->
            //redirecting to add debit activity
            startActivity(Intent(this@dashboard, AddDebitActivity::class.java))
        }
        fab4.setOnClickListener { view ->
            //redirecting to add normal reminder activity
            startActivity(Intent(this@dashboard, AddNReminderActivity::class.java))
        }
        fab5.setOnClickListener { view ->
            //redirecting to add daily reminder activity
            startActivity(Intent(this@dashboard, AddDReminderActivity::class.java))
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_expense, R.id.nav_credits,R.id.nav_debits,R.id.nav_analysis,R.id.nav_daily_reminders,R.id.nav_normal_reminders,R.id.nav_view_reminders
            ), drawerLayout
        )

        //setting DatePicker Widget
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        today = "$day/$month/$year"

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    //functions to handle floating button actions
    private fun showFABMenu(): Unit {
        isFABOpen = true
        //change button color to red
        fab.setImageResource(R.drawable.close)
        fab1.visibility = View.VISIBLE
        fab2.visibility = View.VISIBLE
        fab3.visibility = View.VISIBLE
        fab4.visibility = View.VISIBLE
        fab5.visibility = View.VISIBLE
        fab1.animate().translationY((-90).toFloat())
        fab2.animate().translationY((-135).toFloat())
        fab3.animate().translationY((-180).toFloat())
        fab4.animate().translationY((-225).toFloat())
        fab5.animate().translationY((-270).toFloat())
    }

    private fun closeFABMenu() {
        isFABOpen = false
        //change button color to normal
        fab.setImageResource(R.drawable.plus)
        fab5.animate().translationY(270F)
        fab4.animate().translationY(225F)
        fab3.animate().translationY(180F)
        fab2.animate().translationY(135F)
        fab1.animate().translationY(90F)

        Handler().postDelayed({
            fab5.visibility = View.GONE
            fab4.visibility = View.GONE
            fab3.visibility = View.GONE
            fab2.visibility = View.GONE
            fab1.visibility = View.GONE
        }, 200)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.dashboard, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun displayJson(view: View) {
        val rootObject= JSONObject()
        rootObject.put("name","Vimal")
        rootObject.put("age","22")
        PreferenceManager.getDefaultSharedPreferences(applicationContext).edit().putString("theJson",rootObject.toString()).apply();

        val jsonObject = PreferenceManager.getDefaultSharedPreferences(this).getString("theJson", "")
        Toast.makeText(this.applicationContext,jsonObject,Toast.LENGTH_LONG).show();
    }

    fun showTodaysExpense(view: View) {
        startActivity(Intent(this@dashboard,ViewTodaysExpense::class.java))
    }

    fun searchForExpense(view: View){
        startActivity(Intent(this@dashboard,SearchExpenseActivity::class.java))
    }

    fun clearExpense(item: MenuItem) {
        val calendar = Calendar.getInstance();
        val year = calendar.get(Calendar.YEAR);
        val month = calendar.get(Calendar.MONTH) + 1;
        val day = calendar.get(Calendar.DAY_OF_MONTH);
        var today = "$day/$month/$year"
        Toast.makeText(this@dashboard,"Clearing expenses till $today",Toast.LENGTH_SHORT).show()
        val sharedPreference = SharedPreference(this)
        var temp = ""
        for (i in 1..day){
            temp = sharedPreference.getExpenseData("ExpenseList_$today").toString()
            if(!(temp == "" || temp == null)){
                sharedPreference.pushExpenseData("ExpenseList_$today","")
            }
        }
        Toast.makeText(this@dashboard,"Successfully cleared expense data",Toast.LENGTH_SHORT).show()
    }

    fun logout(item: MenuItem) {
        startActivity(Intent(this@dashboard,Home::class.java))
    }

    fun settings(item: MenuItem) {
        Toast.makeText(this@dashboard,"Work under progress",Toast.LENGTH_SHORT).show()
    }

    fun toBeImplemented(view: View) {
        Toast.makeText(this@dashboard, "Stay tuned, will be released in version 2.0",Toast.LENGTH_SHORT).show()
    }

    fun addDayToDailyReminder(day: String, add: Boolean){
        if(add){
            dailyReminderDays.add(day)
        }
        else{
            dailyReminderDays.removeAt(dailyReminderDays.indexOf(day))
        }
    }

    @SuppressLint("ResourceType")
    fun addDailyReminderDay(view: View) {
        when(view.id){
            R.id.daily_monday -> {
                if(choosen_mon){
                    choosen_mon = false
                    addDayToDailyReminder("Monday",false)
                    findViewById<Button>(R.id.daily_monday).background = ContextCompat.getDrawable(applicationContext, R.layout.rounded_corner_button)
                    findViewById<Button>(R.id.daily_monday).setTextColor(resources.getColor(R.color.black))
                }
                else{
                    choosen_mon = true
                    addDayToDailyReminder("Monday",true)
                    findViewById<Button>(R.id.daily_monday).background = ContextCompat.getDrawable(applicationContext, R.layout.rounded_corner_button_green)
                    findViewById<Button>(R.id.daily_monday).setTextColor(resources.getColor(R.color.white))
                }
            }
            R.id.daily_tuesday -> {
                if(choosen_tue){
                    choosen_tue = false
                    addDayToDailyReminder("Tuesday",false)
                    findViewById<Button>(R.id.daily_tuesday).background = ContextCompat.getDrawable(applicationContext, R.layout.rounded_corner_button)
                    findViewById<Button>(R.id.daily_tuesday).setTextColor(resources.getColor(R.color.black))
                }
                else{
                    choosen_tue = true
                    addDayToDailyReminder("Tuesday",true)
                    findViewById<Button>(R.id.daily_tuesday).background = ContextCompat.getDrawable(applicationContext, R.layout.rounded_corner_button_green)
                    findViewById<Button>(R.id.daily_tuesday).setTextColor(resources.getColor(R.color.white))
                }
            }
            R.id.daily_wednesday -> {
                if(choosen_wed){
                    choosen_wed = false
                    addDayToDailyReminder("Wednesday",false)
                    findViewById<Button>(R.id.daily_wednesday).background = ContextCompat.getDrawable(applicationContext, R.layout.rounded_corner_button)
                    findViewById<Button>(R.id.daily_wednesday).setTextColor(resources.getColor(R.color.black))
                }
                else{
                    choosen_wed = true
                    addDayToDailyReminder("Wednesday",true)
                    findViewById<Button>(R.id.daily_wednesday).background = ContextCompat.getDrawable(applicationContext, R.layout.rounded_corner_button_green)
                    findViewById<Button>(R.id.daily_wednesday).setTextColor(resources.getColor(R.color.white))
                }
            }
            R.id.daily_thursday -> {
                if(choosen_thu){
                    choosen_thu = false
                    addDayToDailyReminder("Thursday",false)
                    findViewById<Button>(R.id.daily_thursday).background = ContextCompat.getDrawable(applicationContext, R.layout.rounded_corner_button)
                    findViewById<Button>(R.id.daily_thursday).setTextColor(resources.getColor(R.color.black))
                }
                else{
                    choosen_thu = true
                    addDayToDailyReminder("Thursday",true)
                    findViewById<Button>(R.id.daily_thursday).background = ContextCompat.getDrawable(applicationContext, R.layout.rounded_corner_button_green)
                    findViewById<Button>(R.id.daily_thursday).setTextColor(resources.getColor(R.color.white))
                }
            }
            R.id.daily_friday -> {
                if(choosen_fri){
                    choosen_fri = false
                    addDayToDailyReminder("Friday",false)
                    findViewById<Button>(R.id.daily_friday).background = ContextCompat.getDrawable(applicationContext, R.layout.rounded_corner_button)
                    findViewById<Button>(R.id.daily_friday).setTextColor(resources.getColor(R.color.black))
                }
                else{
                    choosen_fri = true
                    addDayToDailyReminder("Friday",true)
                    findViewById<Button>(R.id.daily_friday).background = ContextCompat.getDrawable(applicationContext, R.layout.rounded_corner_button_green)
                    findViewById<Button>(R.id.daily_friday).setTextColor(resources.getColor(R.color.white))
                }
            }
            R.id.daily_saturday -> {
                if(choosen_sat){
                    choosen_sat = false
                    addDayToDailyReminder("Saturday",false)
                    findViewById<Button>(R.id.daily_saturday).background = ContextCompat.getDrawable(applicationContext, R.layout.rounded_corner_button)
                    findViewById<Button>(R.id.daily_saturday).setTextColor(resources.getColor(R.color.black))
                }
                else{
                    choosen_sat = true
                    addDayToDailyReminder("Saturday",true)
                    findViewById<Button>(R.id.daily_saturday).background = ContextCompat.getDrawable(applicationContext, R.layout.rounded_corner_button_green)
                    findViewById<Button>(R.id.daily_saturday).setTextColor(resources.getColor(R.color.white))
                }
            }
            R.id.daily_sunday -> {
                if(choosen_sun){
                    choosen_sun = false
                    addDayToDailyReminder("Sunday",false)
                    findViewById<Button>(R.id.daily_sunday).background = ContextCompat.getDrawable(applicationContext, R.layout.rounded_corner_button)
                    findViewById<Button>(R.id.daily_sunday).setTextColor(resources.getColor(R.color.black))
                }
                else{
                    choosen_sun = true
                    addDayToDailyReminder("Sunday",true)
                    findViewById<Button>(R.id.daily_sunday).background = ContextCompat.getDrawable(applicationContext, R.layout.rounded_corner_button_green)
                    findViewById<Button>(R.id.daily_sunday).setTextColor(resources.getColor(R.color.white))
                }
            }
        }
    }

    fun chooseDailyReminderTime(view: View) {
        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            dailyReminderHour = cal.time.hours
            dailyReminderMinute = cal.time.minutes
            dailyReminderTime = SimpleDateFormat("HH:mm").format(cal.time)

            findViewById<Button>(R.id.dailyTimeButton).text = "Choosen time : " + SimpleDateFormat("HH:mm").format(cal.time)
        }
        TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false).show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addDailyReminder(view: View) {
        val task = findViewById<EditText>(R.id.daily_reminder_task).text.toString()
        storeReminderData(true, DailyReminderData(task, dailyReminderTime, dailyReminderDays, ""),NormalReminderData("","","", false))
        val calender: Calendar = Calendar.getInstance()
        calender.set(Calendar.HOUR_OF_DAY, dailyReminderHour)
        calender.set(Calendar.MINUTE, dailyReminderMinute)
        calender.set(Calendar.SECOND, 0)
        Log.v("Test_Vimal", "$dailyReminderHour:$dailyReminderMinute")
        val intent = Intent(applicationContext, DailyNotificationReceiver::class.java)
        val pendingIntent: PendingIntent = PendingIntent.getBroadcast(applicationContext, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmManager: AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calender.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent);
        Log.v("Test_Vimal", "Alarm set")
    }

    fun addNormalReminder(view: View){
        val task = findViewById<EditText>(R.id.normal_reminder_task).text.toString()
        storeReminderData(false, DailyReminderData("","","".split("|").toMutableList(),""),NormalReminderData(task, normalReminderDate, normalReminderTime, true))
    }

    override fun onCreateDialog(id: Int): Dialog? { // TODO Auto-generated method stub
        return if (id == 999) {
            DatePickerDialog(
                this,
                myDateListener, year, month, day
            )
        } else null
    }

    private val myDateListener =
        DatePickerDialog.OnDateSetListener { arg0, arg1, arg2, arg3 ->
            // TODO Auto-generated method stub
            val temp = arg2 + 1
            normalReminderDate = "$arg3/$temp/$arg1"
            findViewById<Button>(R.id.dailyTimeButton).text = "$normalReminderDate at $normalReminderTime"
        }

    fun setDate(view: View?) {
        showDialog(999)
    }

    fun chooseNormalReminderTime(view: View){
        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
        }
        TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false).show()
        normalReminderTime = SimpleDateFormat("HH:mm").format(cal.time)
        setDate(view)
    }

    private fun storeReminderData(isDailyReminder: Boolean, dailyReminderData: DailyReminderData, normalReminderData: NormalReminderData){
        val gson = Gson()
        val sharedPreference = SharedPreference(this)
        if(isDailyReminder){
            //add daily reminder data to db
            var fetchedDailyReminderData = sharedPreference.getDailyReminderData("DailyReminderList")
            val dailyReminderList = fetchedDailyReminderData?.split("|")?.toMutableList()
            if (dailyReminderList != null) {
                dailyReminderList += gson.toJson(dailyReminderData)
                fetchedDailyReminderData = dailyReminderList.joinToString("|")
            }
            if (fetchedDailyReminderData != null) {
                sharedPreference.pushDailyReminderData("DailyReminderList", fetchedDailyReminderData)
            }
            Toast.makeText(applicationContext, "Daily Reminder Set", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@dashboard, dashboard::class.java))
        }
        else{
            //add normal reminder data to db
            var fetchedNormalReminderData = sharedPreference.getNormalReminderData("NormalReminderList")
            val normalReminderList = fetchedNormalReminderData?.split("|")?.toMutableList()
            if (normalReminderList != null) {
                normalReminderList += gson.toJson(normalReminderData)
                fetchedNormalReminderData = normalReminderList.joinToString("|")
            }
            if (fetchedNormalReminderData != null) {
                sharedPreference.pushNormalReminderData("NormalReminderList", fetchedNormalReminderData)
            }
            Toast.makeText(applicationContext, "Normal Reminder Set", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@dashboard, dashboard::class.java))
        }
    }
}
