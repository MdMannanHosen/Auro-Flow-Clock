package com.mannanhosen.auraflowclock.presentation.add_edit_alarm.add_edit_ui_state

data class AlarmAddEditState (
   val text : String = "",
    val hint : String = "",
    val isHintIsVisible : Boolean = true,
   val hour: String = "1",
   val minute: String = "00",
   val period: String = "AM",
    val weekDays : String = ""
)