package com.mannanhosen.auraflowclock.Presentation_Table.Navigation

sealed   class AppRoutes (val routes: String){

    object Home : AppRoutes("home")
    object AlarmSound : AppRoutes("alarm_sound")

    object Snooze : AppRoutes("snooze")

    object Vibration : AppRoutes("vibration")
}