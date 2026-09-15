package com.mannanhosen.auraflowclock.domain.usecase.alarm

import com.mannanhosen.auraflowclock.data.model.entity.Alarm
import com.mannanhosen.auraflowclock.domain.repository.AlarmRepository

class DeleteAlarm(
    private val repository: AlarmRepository
) {
    suspend operator fun invoke (alarm: Alarm) {
        repository.deleteAlarm(alarm)
    }
}