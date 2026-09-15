package com.mannanhosen.auraflowclock.domain.usecase.stopwatch

import com.mannanhosen.auraflowclock.domain.repository.StopwatchRepository


class ClearLaps(
    private val repository: StopwatchRepository
) {
    suspend operator fun invoke(sessionId: Long) {
        repository.clearLapsForSession(sessionId)
    }
}