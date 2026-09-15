package com.mannanhosen.auraflowclock.presentation.stopwatch.components

sealed class StopwatchEvent {
    object StartPause : StopwatchEvent()
    object Lap : StopwatchEvent()
    object Reset : StopwatchEvent()
}