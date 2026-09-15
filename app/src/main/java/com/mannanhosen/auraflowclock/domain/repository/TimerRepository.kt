package com.mannanhosen.auraflowclock.domain.repository


import com.mannanhosen.auraflowclock.data.model.TimerRunState
import kotlinx.coroutines.flow.Flow

interface TimerRepository {
    val runState: Flow<TimerRunState?>
    suspend fun saveRunState(state: TimerRunState)
}