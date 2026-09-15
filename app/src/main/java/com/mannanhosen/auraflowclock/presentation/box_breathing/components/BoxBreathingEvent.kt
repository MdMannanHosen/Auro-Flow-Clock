package com.mannanhosen.auraflowclock.presentation.box_breathing.components

sealed class BoxBreathingEvent {
    object StartPause : BoxBreathingEvent()
    object Stop : BoxBreathingEvent()

    data class SetInhaleSeconds(val seconds: Int) : BoxBreathingEvent()
    data class SetHoldFullSeconds(val seconds: Int) : BoxBreathingEvent()
    data class SetExhaleSeconds(val seconds: Int) : BoxBreathingEvent()
    data class SetHoldEmptySeconds(val seconds: Int) : BoxBreathingEvent()

    data class SetSessionDuration(val seconds: Int) : BoxBreathingEvent()
}