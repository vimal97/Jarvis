package com.example.jarvis

//import android.R

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.view.Menu
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import org.json.JSONObject


@Suppress("DEPRECATION")
class dashboard : AppCompatActivity() {

    private lateinit var fab: FloatingActionButton
    private lateinit var fab1: LinearLayout
    private lateinit var fab2: LinearLayout
    private lateinit var fab3: LinearLayout
    private lateinit var fab4: LinearLayout
    private lateinit var fab5: LinearLayout
    private var isFABOpen: Boolean = false

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        fab1 = findViewById<LinearLayout>(R.id.add_credit_menu)
        fab2 = findViewById<LinearLayout>(R.id.add_credit_menu_1)
        fab3 = findViewById<LinearLayout>(R.id.add_credit_menu_2)
        fab4 = findViewById<LinearLayout>(R.id.add_credit_menu_3)
        fab5 = findViewById<LinearLayout>(R.id.add_credit_menu_4)
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
}