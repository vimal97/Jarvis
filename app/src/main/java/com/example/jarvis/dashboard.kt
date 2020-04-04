package com.example.jarvis

//import android.R

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
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

    private lateinit var fab1: LinearLayout
    private var isFABOpen: Boolean = false

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        fab1 = findViewById<LinearLayout>(R.id.add_credit_menu)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        val fab: FloatingActionButton = findViewById(R.id.fab_main)
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
        fab1.visibility = View.VISIBLE
        fab1.animate().translationY((-90).toFloat())
    }

    private fun closeFABMenu() {
        isFABOpen = false
        fab1.animate().translationY(0F)
        fab1.visibility = View.GONE
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
