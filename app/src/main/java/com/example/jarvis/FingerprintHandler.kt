package com.example.jarvis

//import android.support.v4.app.ActivityCompat
import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.fingerprint.FingerprintManager
import android.media.MediaPlayer
import android.os.Build
import android.os.CancellationSignal
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.startActivity
import java.util.*

@RequiresApi(Build.VERSION_CODES.M)
class FingerprintHandler(private val appContext: Context) : FingerprintManager.AuthenticationCallback() {

    private var cancellationSignal: CancellationSignal? = null
    private lateinit var mediaPlayer: MediaPlayer


    @RequiresApi(Build.VERSION_CODES.M)
    fun startAuth(manager: FingerprintManager,
                  cryptoObject: FingerprintManager.CryptoObject) {

        cancellationSignal = CancellationSignal()

        if (ActivityCompat.checkSelfPermission(appContext,
                Manifest.permission.USE_FINGERPRINT) !=
            PackageManager.PERMISSION_GRANTED) {
            return
        }
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null)
        print("reached end of startAuth")
    }

    override fun onAuthenticationError(errMsgId: Int,
                                        errString: CharSequence) {
        Toast.makeText(appContext,
            "Authentication error\n" + errString,
            Toast.LENGTH_LONG).show()
    }

    override fun onAuthenticationHelp(helpMsgId: Int,
                                      helpString: CharSequence) {
        Toast.makeText(appContext,
            "Authentication help\n" + helpString,
            Toast.LENGTH_LONG).show()
    }

    override fun onAuthenticationFailed() {
        speakWishMessage(false)
        print("Failed to authenticate dude")
        return
    }

    override fun onAuthenticationSucceeded(result: FingerprintManager.AuthenticationResult) {
        print("Success dude")
        speakWishMessage(true)
    }

    private fun speakWishMessage(value: Boolean){
        if(value){
            //play the success tone
            //check for the particular wish
            val c: Calendar = Calendar.getInstance()

            when (c.get(Calendar.HOUR_OF_DAY)) {
                in 0..11 -> {
                    //morning
                    mediaPlayer = MediaPlayer.create(appContext,R.raw.morning_wish)
                    mediaPlayer.start()
                    val success = Home()
                    success.success()
                    return
                }
                in 12..15 -> {
                    //afternoon
                    mediaPlayer = MediaPlayer.create(appContext,R.raw.afternoon_wish)
                    mediaPlayer.start()
                    val success = Home()
                    success.success()
                    return
                }
                in 16..20 -> {
                    //evening
                    mediaPlayer = MediaPlayer.create(appContext,R.raw.evening_wish)
                    mediaPlayer.start()
                    val success = Home()
                    success.success()
                    return
                }
                in 21..23 -> {
                    //night
                    mediaPlayer = MediaPlayer.create(appContext,R.raw.night_wish)
                    mediaPlayer.start()
                    val success = Home()
                    success.success()
                    return
                }
            }
        }
        else{
            //play the failed tone
            mediaPlayer = MediaPlayer.create(appContext,R.raw.login_failure)
            mediaPlayer.start()
        }
    }
}