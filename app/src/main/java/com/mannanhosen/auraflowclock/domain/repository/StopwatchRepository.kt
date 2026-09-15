package com.mannanhosen.auraflowclock.domain.repository

import com.mannanhosen.auraflowclock.data.data_source.StopwatchPreferences
import com.mannanhosen.auraflowclock.data.model.StopwatchLap
import com.mannanhosen.auraflowclock.data.model.StopwatchRunState
import kotlinx.coroutines.flow.Flow

interface StopwatchRepository {

    fun getLapsForSession(sessionId: Long) : Flow<List<StopwatchLap>>
    suspend fun insertLap(lap: StopwatchLap): Long
    suspend fun clearLapsForSession(sessionId: Long)
    suspend fun clearAllLaps()

    val runState: Flow<StopwatchRunState?>
    suspend fun saveRunState(state: StopwatchRunState)
}
