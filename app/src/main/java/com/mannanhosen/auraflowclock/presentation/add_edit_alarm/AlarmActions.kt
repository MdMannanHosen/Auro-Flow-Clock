package com.mannanhosen.auraflowclock.presentation.add_edit_alarm

import com.mannanhosen.auraflowclock.data.model.entity.Alarm

interface AlarmActions {
    fun updateAlarmCreationState(alarm: Alarm)
    fun save() {}
}