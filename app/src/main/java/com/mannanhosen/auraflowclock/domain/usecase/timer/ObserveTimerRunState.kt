package com.mannanhosen.auraflowclock.domain.usecase.timer


import com.mannanhosen.auraflowclock.data.model.TimerRunState
import com.mannanhosen.auraflowclock.domain.repository.TimerRepository
import kotlinx.coroutines.flow.Flow

class ObserveTimerRunState(
    private val repository: TimerRepository
) {
    operator fun invoke(): Flow<TimerRunState?> = repository.runState
}