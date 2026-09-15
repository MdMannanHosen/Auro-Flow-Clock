package com.mannanhosen.auraflowclock.domain.usecase.stopwatch



import com.mannanhosen.auraflowclock.data.model.StopwatchRunState
import com.mannanhosen.auraflowclock.domain.repository.StopwatchRepository

class PersistRunState(
    private val repository: StopwatchRepository
) {
    suspend operator fun invoke(state: StopwatchRunState) {
        repository.saveRunState(state)
    }
}