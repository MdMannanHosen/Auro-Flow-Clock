package com.mannanhosen.auraflowclock.Presentation_Table.alarm

import com.mannanhosen.auraflowclock.Data_Kitchen.Model.Alarm

interface AlarmActions {
    fun updateAlarmCreationState(alarm: Alarm)
    fun save() {}
}