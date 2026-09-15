package com.mannanhosen.auraflowclock.presentation.stopwatch

import com.mannanhosen.auraflowclock.data.model.StopwatchLap

data class Stopwatch0State(
    val elapsedMillis: Long = 0L,
    val isRunning: Boolean = false,
    val laps: List<StopwatchLap> = emptyList(),
    val bestLapId: Int? = null,   // sobcheye druto lap - green e highlight hobe
    val worstLapId: Int? = null   // sobcheye dhire lap - red e highlight hobe
)
