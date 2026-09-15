package com.mannanhosen.auraflowclock.presentation.timer.components

data class TimerState(
    val totalDurationMillis: Long = 5 * 60_000L,
    val remainingMillis: Long = 5 * 60_000L,
    val isRunning: Boolean = false
) {
    /** 1f = full ring, 0f = empty - progress ring আঁকতে ব্যবহার হয় */
    val progressFraction: Float
        get() = if (totalDurationMillis <= 0L) 0f
        else (remainingMillis.toFloat() / totalDurationMillis.toFloat()).coerceIn(0f, 1f)

    val isIdle: Boolean get() = !isRunning && remainingMillis == totalDurationMillis
    val isPaused: Boolean get() = !isRunning && remainingMillis in 1 until totalDurationMillis
    val isFinished: Boolean get() = remainingMillis <= 0L
}