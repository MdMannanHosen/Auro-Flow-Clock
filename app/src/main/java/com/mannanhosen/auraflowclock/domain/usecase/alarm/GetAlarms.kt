package com.mannanhosen.auraflowclock.domain.usecase.alarm

import com.mannanhosen.auraflowclock.data.model.entity.Alarm
import com.mannanhosen.auraflowclock.domain.repository.AlarmRepository
import kotlinx.coroutines.flow.Flow

class GetAlarms(
    private val repository: AlarmRepository
) {
    operator fun invoke(): Flow<List<Alarm>> {
        return repository.getAlarms()
    }
}