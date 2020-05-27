package com.example.jarvis.ui

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.jarvis.R
import com.example.jarvis.dashboard
import java.nio.channels.Channel

@Suppress("DEPRECATION")
class DailyNotificationReceiver: BroadcastReceiver() {

    private var notificationManager: NotificationManager? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context, intent: Intent?) {
        val CHANNEL_ID = "com.example.jarvis"
        //creating the channel
        createNotificationChannel(
            "com.example.jarvis",
            "Jarvis Daily Reminder",
            "Example News Channel")
        val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val newActivityIntent: Intent = Intent(context, dashboard::class.java)
        newActivityIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 100, newActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        Log.v("Test_Vimal", "Alarm notified")
        val builder: Notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.plus)
            .setContentIntent(pendingIntent)
            .setContentTitle("Time to take oil")
            .setContentText("Sample Text")
            .build()
        notificationManager.notify(100,builder)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(id: String, name: String,
                                          description: String) {

        val importance = NotificationManager.IMPORTANCE_LOW
        val channel = NotificationChannel(id, name, importance)

        channel.description = description
        channel.enableLights(true)
        channel.lightColor = Color.RED
        channel.enableVibration(true)
        channel.vibrationPattern =
            longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
        notificationManager?.createNotificationChannel(channel)
    }
}