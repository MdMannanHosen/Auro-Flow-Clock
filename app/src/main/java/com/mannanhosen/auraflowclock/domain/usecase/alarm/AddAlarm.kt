package com.mannanhosen.auraflowclock.domain.usecase.alarm

import com.mannanhosen.auraflowclock.data.model.entity.Alarm
import com.mannanhosen.auraflowclock.data.model.entity.InvalidAlarmException
import com.mannanhosen.auraflowclock.domain.repository.AlarmRepository

class AddAlarm(
    private val repository: AlarmRepository
) {
    @Throws (InvalidAlarmException::class)
    suspend operator fun invoke( alarm: Alarm) {
       if(alarm.title.isBlank()) {
        throw InvalidAlarmException("The title of the alarm can not empty")
       }
      repository.insertAlarm(alarm)
    }

}