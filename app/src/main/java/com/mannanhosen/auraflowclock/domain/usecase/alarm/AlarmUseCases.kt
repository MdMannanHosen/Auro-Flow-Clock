package com.mannanhosen.auraflowclock.domain.usecase.alarm

import com.mannanhosen.auraflowclock.domain.usecase.alarm.DeleteAlarm
import com.mannanhosen.auraflowclock.domain.usecase.alarm.GetAlarm
import com.mannanhosen.auraflowclock.domain.usecase.alarm.GetAlarms

data class AlarmUseCases (

    val addAlarm: AddAlarm,
    val deleteAlarm: DeleteAlarm,
    val getAlarm: GetAlarm,
    val getAlarms: GetAlarms,
)