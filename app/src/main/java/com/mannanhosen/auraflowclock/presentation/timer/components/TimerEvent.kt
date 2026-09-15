package com.mannanhosen.auraflowclock.presentation.timer.components


sealed class TimerEvent {
    data class SelectPreset(val minutes: Int) : TimerEvent()
    data class AdjustDuration(val deltaMinutes: Int) : TimerEvent()
    object StartPause : TimerEvent()
    object AddOneMinute : TimerEvent()
    object Reset : TimerEvent()
}