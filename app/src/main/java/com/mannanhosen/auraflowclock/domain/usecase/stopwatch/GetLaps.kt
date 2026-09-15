package com.mannanhosen.auraflowclock.domain.usecase.stopwatch


import com.mannanhosen.auraflowclock.data.model.StopwatchLap
import com.mannanhosen.auraflowclock.domain.repository.StopwatchRepository
import kotlinx.coroutines.flow.Flow

class GetLaps(
    private val repository: StopwatchRepository
) {
    operator fun invoke(sessionId: Long): Flow<List<StopwatchLap>> =
        repository.getLapsForSession(sessionId)
}