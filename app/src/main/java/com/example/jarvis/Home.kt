package com.example.jarvis

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.PromptInfo
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import java.lang.Exception
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import kotlin.system.exitProcess


class Home : AppCompatActivity() {

    var executor: Executor =
        Executors.newSingleThreadExecutor()
    val activity: FragmentActivity = this
    private lateinit var mediaPlayer: MediaPlayer


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //hiding title bar
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }
        setContentView(R.layout.activity_home)
        findViewById<EditText>(R.id.authPin).setOnEditorActionListener {
            v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                login()
                true
            }
            else{
                false
            }
        }

        //check if new user and obtain PIN if it is
        val sharedPreference = SharedPreference(this)
        val pass = sharedPreference.getLoginCredentials()
        if(pass == "" || pass == null || pass == "null"){
            //ask for setting the login PIN
            Log.v("Test_Vimal", "Password : $pass")
            changePin()
        }
        else{
            val biometricPrompt =
                BiometricPrompt(activity, executor, object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationError(
                        errorCode: Int,
                        errString: CharSequence
                    ) {
                        super.onAuthenticationError(errorCode, errString)
                        runOnUiThread {
                            if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) { // user clicked negative button
                                Toast.makeText(applicationContext, "You can't skip this step", Toast.LENGTH_SHORT).show()
                            } else {  // Called when an unrecoverable error has been encountered and the operation is complete.
                                Toast.makeText(applicationContext, "Something unexpected happen, try again", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        speakWishMessage(true)
                        startActivity(Intent(this@Home, dashboard::class.java))
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        //Called when a biometric is valid but not recognized.
                        speakWishMessage(false)
                    }
                })

            val promptInfo = PromptInfo.Builder()
                .setTitle("Let me first verify its you")
                .setSubtitle("Swipe your finger across the sensor")
                .setNegativeButtonText("Cancel")
                .build()

            biometricPrompt.authenticate(promptInfo)
        }
    }

    private fun login() {
        val sharedPreference = SharedPreference(this)
        val pass = sharedPreference.getLoginCredentials()
        if(pass != "" && pass != null && pass != "null"){
            if(pass == findViewById<EditText>(R.id.authPin).text.toString()){
                Log.v("Test_Vimal", "Password Matched")
                speakWishMessage(true)
                startActivity(Intent(applicationContext, dashboard::class.java))
            }
            else{
                findViewById<EditText>(R.id.authPin).setText("")
                speakWishMessage(false)
                Log.v("Test_Vimal", "Wrong password !!")
            }
        }
        else{
            Toast.makeText(applicationContext, "Please set the PIN before login !!", Toast.LENGTH_LONG).show()
            Log.v("Test_Vimal", "Credentials doesn't exist")
        }
    }

    private fun changePin() {
        var  builder: AlertDialog.Builder = AlertDialog.Builder(this)
        val pinview = LayoutInflater.from(applicationContext).inflate(R.layout.setauthpin, null, false)
        builder.setView(pinview)
            .setNegativeButton("Cancel", DialogInterface.OnClickListener(){
                    dialigInterface: DialogInterface, i : Int ->
                Log.v("Test_Vimal", "PIN Setting cancelled")
                Toast.makeText(applicationContext, "You cannot continue without setting PIN.", Toast.LENGTH_SHORT).show()
                startActivity(Intent(applicationContext, Home::class.java))
            })
            .setPositiveButton("Ok", DialogInterface.OnClickListener(){
                    _: DialogInterface, _: Int ->
                val newPin = pinview.findViewById<EditText>(R.id.pin).text.toString()
                if(newPin != ""){
                    val sharedPreference = SharedPreference(this)
                    sharedPreference.setLoginCredentials(newPin)
                    Log.v("Test_Vimal", "PIN Added")
                    Toast.makeText(applicationContext, "PIN added successfully", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(applicationContext, dashboard::class.java))
                }
            })
        builder.create().show()
    }

    private fun speakWishMessage(value: Boolean){
        if(value){
            //play the success tone
            //check for the particular wish
            val c: Calendar = Calendar.getInstance()
            when (c.get(Calendar.HOUR_OF_DAY)) {
                in 0..11 -> {
                    //morning
                    mediaPlayer = MediaPlayer.create(applicationContext,R.raw.morning_wish)
                    mediaPlayer.start()
                    return
                }
                in 12..15 -> {
                    //afternoon
                    mediaPlayer = MediaPlayer.create(applicationContext,R.raw.afternoon_wish)
                    mediaPlayer.start()
                    return
                }
                in 16..20 -> {
                    //evening
                    mediaPlayer = MediaPlayer.create(applicationContext,R.raw.evening_wish)
                    mediaPlayer.start()
                    return
                }
                in 21..23 -> {
                    //night
                    mediaPlayer = MediaPlayer.create(applicationContext,R.raw.night_wish)
                    mediaPlayer.start()
                    return
                }
            }
        }
        else{
            //play the failed tone
            mediaPlayer = MediaPlayer.create(applicationContext,R.raw.login_failure)
            mediaPlayer.start()
        }
    }
}