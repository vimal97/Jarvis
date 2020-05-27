package com.example.jarvis

data class DailyReminderData(var task: String, var timeToRemind: String, var daysToRemind: MutableList<String>, var alarmTonePath: String)