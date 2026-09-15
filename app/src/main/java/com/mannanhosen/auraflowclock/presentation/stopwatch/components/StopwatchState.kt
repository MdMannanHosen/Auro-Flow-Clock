package com.mannanhosen.auraflowclock.presentation.stopwatch.components


import com.mannanhosen.auraflowclock.data.model.StopwatchLap

data class StopwatchState(
    val elapsedMillis: Long = 0L,
    val isRunning: Boolean = false,
    val laps: List<StopwatchLap> = emptyList(),
    // ⚠️ StopwatchLap.id আসলে Int, তাই এখানেও Int? — Long? দিলে type mismatch হবে
    val bestLapId: Int? = null,   // সবচেয়ে দ্রুত lap - green এ highlight হবে
    val worstLapId: Int? = null   // সবচেয়ে ধীর lap - red এ highlight হবে
)