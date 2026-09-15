package com.mannanhosen.auraflowclock.domain.usecase.stopwatch
import com.mannanhosen.auraflowclock.data.model.StopwatchRunState
import com.mannanhosen.auraflowclock.domain.repository.StopwatchRepository
import kotlinx.coroutines.flow.Flow

class ObserveRunState(
    private val repository: StopwatchRepository
) {
    operator fun invoke(): Flow<StopwatchRunState?> = repository.runState
}