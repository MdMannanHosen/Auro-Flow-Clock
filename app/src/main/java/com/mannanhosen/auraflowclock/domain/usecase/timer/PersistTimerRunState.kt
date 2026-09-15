package com.mannanhosen.auraflowclock.domain.usecase.timer


import com.mannanhosen.auraflowclock.data.model.TimerRunState
import com.mannanhosen.auraflowclock.domain.repository.TimerRepository

class PersistTimerRunState(
    private val repository: TimerRepository
) {
    suspend operator fun invoke(state: TimerRunState) {
        repository.saveRunState(state)
    }
}