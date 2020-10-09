package com.example.jarvis

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.util.Log
import java.util.ArrayList

class SharedPreference(val context: Context) {
    private val prefsName = "Jarvis"

    fun pushCreditData(KEY_NAME: String,value: String){
        var sharedPref: SharedPreferences = context.getSharedPreferences("Jarvis", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(KEY_NAME, value)
        editor.commit()
    }

    fun pushExpenseData(KEY_NAME: String,value: String){
        var sharedPref: SharedPreferences = context.getSharedPreferences("Jarvis", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(KEY_NAME, value)
        editor.commit()
    }

    fun getExpenseData(KEY_NAME: String): String?{
        var sharedPref: SharedPreferences = context.getSharedPreferences("Jarvis",MODE_PRIVATE)
        return sharedPref.getString(KEY_NAME, null)
    }

    fun getCreditData(KEY_NAME: String): String? {
        var sharedPref: SharedPreferences = context.getSharedPreferences("Jarvis",MODE_PRIVATE)
        return sharedPref.getString(KEY_NAME, null)
    }

    fun pushDebitData(KEY_NAME: String, value: String) {
        var sharedPref: SharedPreferences = context.getSharedPreferences("Jarvis", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(KEY_NAME, value)
        editor.commit()
    }

    fun pushDailyReminderData(KEY_NAME: String, value: String){
        var sharedPref: SharedPreferences = context.getSharedPreferences("Jarvis", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(KEY_NAME, value)
        editor.commit()
    }

    fun pushNormalReminderData(KEY_NAME: String, value: String){
        var sharedPref: SharedPreferences = context.getSharedPreferences("Jarvis", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(KEY_NAME, value)
        editor.commit()
    }

    fun pushProfileData(KEY_NAME: String, value: String){
        var sharedPref: SharedPreferences = context.getSharedPreferences("Jarvis", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(KEY_NAME, value)
        editor.commit()
    }
    fun pushSecretNotesList(KEY_NAME: String, value: String){
        var sharedPref: SharedPreferences = context.getSharedPreferences("Jarvis", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(KEY_NAME, value)
        editor.commit()
    }

    fun getSecretNotesList(KEY_NAME: String): String? {
        var sharedPref: SharedPreferences = context.getSharedPreferences("Jarvis",MODE_PRIVATE)
        return sharedPref.getString(KEY_NAME, null)
    }

    fun getProfileData(KEY_NAME: String): String?{
        var sharedPref: SharedPreferences = context.getSharedPreferences("Jarvis",MODE_PRIVATE)
        return sharedPref.getString(KEY_NAME, null)
    }

    fun getDailyReminderData(KEY_NAME: String): String? {
        var sharedPref: SharedPreferences = context.getSharedPreferences("Jarvis",MODE_PRIVATE)
        return sharedPref.getString(KEY_NAME, null)
    }

    fun getNormalReminderData(KEY_NAME: String): String? {
        var sharedPref: SharedPreferences = context.getSharedPreferences("Jarvis",MODE_PRIVATE)
        return sharedPref.getString(KEY_NAME, null)
    }

    fun getDebitData(KEY_NAME: String): String? {
        var sharedPref: SharedPreferences = context.getSharedPreferences("Jarvis",MODE_PRIVATE)
        return sharedPref.getString(KEY_NAME, null)
    }

    fun clearSharedPreference() {
        var sharedPref: SharedPreferences = context.getSharedPreferences("Jarvis",MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.clear()
        editor.commit()
    }

    fun removeData(KEY_NAME: String) {
        var sharedPref: SharedPreferences = context.getSharedPreferences("Jarvis",MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.remove(KEY_NAME)
        editor.commit()
    }

    fun getLoginCredentials(): String? {
        var sharedPref: SharedPreferences = context.getSharedPreferences("Jarvis",MODE_PRIVATE)
        return sharedPref.getString("Jarvis_Pass", null)
    }

    fun setLoginCredentials(PASS: String){
        var sharedPref: SharedPreferences = context.getSharedPreferences("Jarvis", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString("Jarvis_Pass", PASS)
        Log.v("Test_Vimal", "Received pass is $PASS and Added pass is : " + sharedPref.getString("Jarvis_Pass", null))
    }
}