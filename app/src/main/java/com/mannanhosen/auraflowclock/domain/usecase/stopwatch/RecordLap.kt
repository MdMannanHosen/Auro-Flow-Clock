package com.mannanhosen.auraflowclock.domain.usecase.stopwatch



import com.mannanhosen.auraflowclock.data.model.StopwatchLap
import com.mannanhosen.auraflowclock.domain.repository.StopwatchRepository

class RecordLap(
    private val repository: StopwatchRepository
) {
    suspend operator fun invoke(
        sessionId: Long,
        lapNumber: Int,
        totalTimeMillis: Long,
        previousTotalMillis: Long
    ) {
        repository.insertLap(
            StopwatchLap(
                sessionId = sessionId,
                lapNumber = lapNumber,
                totalTimeMillis = totalTimeMillis,
                lapTimeMillis = totalTimeMillis - previousTotalMillis
            )
        )
    }
}