package com.mannanhosen.auraflowclock.presentation.box_breathing.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mannanhosen.auraflowclock.data.model.BoxBreathingSettings
import com.mannanhosen.auraflowclock.domain.usecase.box_breathing.BoxBreathingUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

private val phaseOrder = listOf(
    BoxBreathingPhase.INHALE,
    BoxBreathingPhase.HOLD_FULL,
    BoxBreathingPhase.EXHALE,
    BoxBreathingPhase.HOLD_EMPTY
)

@HiltViewModel
class BoxBreathingViewModel @Inject constructor(
    private val useCases: BoxBreathingUseCases
) : ViewModel() {

    var state by mutableStateOf(BoxBreathingState())
        private set

    private var tickerJob: Job? = null
    private var countdownJob: Job? = null

    init {
        viewModelScope.launch {
            val saved = useCases.observeSettings().first() ?: BoxBreathingSettings()
            state = state.copy(
                inhaleSeconds = saved.inhaleSeconds,
                holdFullSeconds = saved.holdFullSeconds,
                exhaleSeconds = saved.exhaleSeconds,
                holdEmptySeconds = saved.holdEmptySeconds,
                sessionDurationSeconds = saved.sessionDurationSeconds,
                phaseSecondsLeft = saved.inhaleSeconds
            )
        }
    }

    fun onEvent(event: BoxBreathingEvent) {
        when (event) {
            is BoxBreathingEvent.StartPause -> toggleStartPause()
            is BoxBreathingEvent.Stop -> stop()
            is BoxBreathingEvent.SetInhaleSeconds -> setInhale(event.seconds)
            is BoxBreathingEvent.SetHoldFullSeconds -> setHoldFull(event.seconds)
            is BoxBreathingEvent.SetExhaleSeconds -> setExhale(event.seconds)
            is BoxBreathingEvent.SetHoldEmptySeconds -> setHoldEmpty(event.seconds)
            is BoxBreathingEvent.SetSessionDuration -> setSessionDuration(event.seconds)
        }
    }

    private fun settingsEditable() = !state.isRunning && !state.isCountingDown

    private fun setInhale(seconds: Int) {
        if (!settingsEditable()) return
        val clamped = seconds.coerceIn(2, 12)
        state = state.copy(
            inhaleSeconds = clamped,
            phaseSecondsLeft = if (state.currentPhase == BoxBreathingPhase.INHALE) clamped else state.phaseSecondsLeft
        )
        persist()
    }

    private fun setHoldFull(seconds: Int) {
        if (!settingsEditable()) return
        val clamped = seconds.coerceIn(2, 12)
        state = state.copy(
            holdFullSeconds = clamped,
            phaseSecondsLeft = if (state.currentPhase == BoxBreathingPhase.HOLD_FULL) clamped else state.phaseSecondsLeft
        )
        persist()
    }

    private fun setExhale(seconds: Int) {
        if (!settingsEditable()) return
        val clamped = seconds.coerceIn(2, 12)
        state = state.copy(
            exhaleSeconds = clamped,
            phaseSecondsLeft = if (state.currentPhase == BoxBreathingPhase.EXHALE) clamped else state.phaseSecondsLeft
        )
        persist()
    }

    private fun setHoldEmpty(seconds: Int) {
        if (!settingsEditable()) return
        val clamped = seconds.coerceIn(2, 12)
        state = state.copy(
            holdEmptySeconds = clamped,
            phaseSecondsLeft = if (state.currentPhase == BoxBreathingPhase.HOLD_EMPTY) clamped else state.phaseSecondsLeft
        )
        persist()
    }

    private fun setSessionDuration(seconds: Int) {
        if (!settingsEditable()) return
        state = state.copy(sessionDurationSeconds = seconds)
        persist()
    }

    private fun toggleStartPause() {
        if (state.isRunning) {
            // ⏸️ pause - কোনো countdown লাগে না
            tickerJob?.cancel()
            state = state.copy(isRunning = false)
            return
        }

        if (state.isCountingDown) return // countdown চলাকালীন duplicate tap ignore

        val isFreshStart = state.isSessionComplete || state.isIdle

        if (isFreshStart) {
            if (state.isSessionComplete) {
                state = state.copy(
                    currentPhase = BoxBreathingPhase.INHALE,
                    phaseSecondsLeft = state.inhaleSeconds,
                    cycleCount = 0,
                    sessionSecondsElapsed = 0,
                    isSessionComplete = false
                )
            }
            startCountdown()
        } else {
            // ▶️ pause থেকে resume - countdown ছাড়াই সরাসরি চলতে থাকে
            state = state.copy(isRunning = true)
            startTicker()
        }
    }

    /** ✅ notun - Start chapar por 3, 2, 1 dekhiye tarpor asol session shuru hoy */
    private fun startCountdown() {
        countdownJob?.cancel()
        state = state.copy(isCountingDown = true, countdownValue = 3)
        countdownJob = viewModelScope.launch {
            for (value in intArrayOf(3, 2, 1)) {
                state = state.copy(countdownValue = value)
                delay(1000L)
            }
            state = state.copy(isCountingDown = false, isRunning = true)
            startTicker()
        }
    }

    private fun stop() {
        tickerJob?.cancel()
        countdownJob?.cancel()
        state = state.copy(
            isRunning = false,
            isCountingDown = false,
            currentPhase = BoxBreathingPhase.INHALE,
            phaseSecondsLeft = state.inhaleSeconds,
            cycleCount = 0,
            sessionSecondsElapsed = 0,
            isSessionComplete = false
        )
    }

    private fun startTicker() {
        tickerJob?.cancel()
        tickerJob = viewModelScope.launch {
            while (true) {
                delay(1000L)

                val sessionElapsed = state.sessionSecondsElapsed + 1
                if (sessionElapsed >= state.sessionDurationSeconds) {
                    // ✅ session shesh - completion celebration UI dekhabe
                    state = state.copy(
                        isRunning = false,
                        sessionSecondsElapsed = state.sessionDurationSeconds,
                        isSessionComplete = true
                    )
                    break
                }

                val secondsLeft = state.phaseSecondsLeft - 1
                if (secondsLeft > 0) {
                    state = state.copy(phaseSecondsLeft = secondsLeft, sessionSecondsElapsed = sessionElapsed)
                } else {
                    val currentIndex = phaseOrder.indexOf(state.currentPhase)
                    val nextIndex = (currentIndex + 1) % phaseOrder.size
                    val nextPhase = phaseOrder[nextIndex]
                    val completedFullCycle = nextIndex == 0

                    state = state.copy(
                        currentPhase = nextPhase,
                        phaseSecondsLeft = state.durationFor(nextPhase),
                        sessionSecondsElapsed = sessionElapsed,
                        cycleCount = if (completedFullCycle) state.cycleCount + 1 else state.cycleCount
                    )
                }
            }
        }
    }

    private fun persist() {
        viewModelScope.launch {
            useCases.saveSettings(
                BoxBreathingSettings(
                    inhaleSeconds = state.inhaleSeconds,
                    holdFullSeconds = state.holdFullSeconds,
                    exhaleSeconds = state.exhaleSeconds,
                    holdEmptySeconds = state.holdEmptySeconds,
                    sessionDurationSeconds = state.sessionDurationSeconds
                )
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        tickerJob?.cancel()
        countdownJob?.cancel()
    }
}