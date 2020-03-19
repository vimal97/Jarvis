package com.example.jarvis

//import android.Manifest
//import android.os.Bundle
//import android.util.Log
//import android.hardware.biometrics.BiometricPrompt
//import android.os.Build
//import androidx.annotation.RequiresApi
//import androidx.appcompat.app.AppCompatActivity
//import android.app.KeyguardManager
//import android.content.Context
//import android.content.Intent
//import android.content.pm.PackageManager
//import android.hardware.fingerprint.FingerprintManager
//import android.security.keystore.KeyProperties
//import android.widget.Toast
//import androidx.core.app.ActivityCompat
//import java.security.KeyStore
//import java.security.NoSuchAlgorithmException
//import java.security.NoSuchProviderException
//import javax.crypto.KeyGenerator
//import android.security.keystore.KeyGenParameterSpec
//import java.security.cert.CertificateException
//import java.security.InvalidAlgorithmParameterException
//import java.io.IOException
//import android.security.keystore.KeyPermanentlyInvalidatedException
//import java.security.InvalidKeyException
//import java.security.KeyStoreException
//import java.security.UnrecoverableKeyException
//import javax.crypto.NoSuchPaddingException
//import javax.crypto.SecretKey
//import javax.crypto.Cipher
//import android.os.CancellationSignal

//import android.R

import android.accessibilityservice.GestureDescription
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.PromptInfo
import androidx.fragment.app.FragmentActivity
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors


class Home : AppCompatActivity() {

//    private var fingerprintManager: FingerprintManager? = null
//    private var keyguardManager: KeyguardManager? = null
//    private var keyStore: KeyStore? = null
//    private var keyGenerator: KeyGenerator? = null
//    private val KEY_NAME = "example_key"
//    private var cipher: Cipher? = null
//    private var cryptoObject: FingerprintManager.CryptoObject? = null

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

        val biometricPrompt =
            BiometricPrompt(activity, executor, object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) { // user clicked negative button
//Toast.makeText(activity, "Operation Cancelled By User!", Toast.LENGTH_SHORT).show();
                    } else { //Toast.makeText(activity, "Unknown Error!", Toast.LENGTH_SHORT).show();
// Called when an unrecoverable error has been encountered and the operation is complete.
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