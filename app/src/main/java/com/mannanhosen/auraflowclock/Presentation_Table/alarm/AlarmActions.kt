package com.mannanhosen.auraflowclock.Presentation_Table.alarm

import com.mannanhosen.auraflowclock.Domain_MenuCard.Model.Alarm

interface AlarmActions {
    fun updateAlarmCreationState(alarm: Alarm)
    fun save() {}
}