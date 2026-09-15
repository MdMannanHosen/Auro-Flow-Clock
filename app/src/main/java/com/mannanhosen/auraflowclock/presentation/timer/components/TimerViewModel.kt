package com.mannanhosen.auraflowclock.presentation.timer.components


import android.os.SystemClock
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mannanhosen.auraflowclock.data.model.TimerRunState
import com.mannanhosen.auraflowclock.domain.usecase.timer.TimerUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val MIN_DURATION_MILLIS = 60_000L          // 1 min floor
private const val MAX_DURATION_MILLIS = 180 * 60_000L    // 3 hour cap

@HiltViewModel
class TimerViewModel @Inject constructor(
    private val useCases: TimerUseCases
) : ViewModel() {

    var state by mutableStateOf(TimerState())
        private set

    // ⏱️ running obosthay target = SystemClock.elapsedRealtime() + remaining. Deep sleep,
    //    app kill - kono kichute somoy hariye jay na, ঠিক Stopwatch er moto principle.
    private var targetElapsedRealtime = 0L
    private var tickerJob: Job? = null

    init {
        viewModelScope.launch {
            val saved = useCases.observeRunState().first() ?: TimerRunState()
            targetElapsedRealtime = saved.targetElapsedRealtime

            val remaining = if (saved.isRunning) {
                (saved.targetElapsedRealtime - SystemClock.elapsedRealtime()).coerceAtLeast(0L)
            } else {
                saved.remainingMillis
            }

            state = state.copy(
                totalDurationMillis = saved.totalDurationMillis,
                remainingMillis = remaining,
                isRunning = saved.isRunning && remaining > 0L
            )

            if (state.isRunning) startTicker()
        }
    }

    fun onEvent(event: TimerEvent) {
        when (event) {
            is TimerEvent.SelectPreset -> selectPreset(event.minutes)
            is TimerEvent.AdjustDuration -> adjustDuration(event.deltaMinutes)
            is TimerEvent.StartPause -> toggleStartPause()
            is TimerEvent.AddOneMinute -> addOneMinute()
            is TimerEvent.Reset -> reset()
        }
    }

    private fun selectPreset(minutes: Int) {
        if (state.isRunning) return
        val millis = (minutes * 60_000L).coerceIn(MIN_DURATION_MILLIS, MAX_DURATION_MILLIS)
        state = state.copy(totalDurationMillis = millis, remainingMillis = millis)
        persist()
    }

    /** Idle/Paused obosthay ring-e drag kore duration change korar jonno */
    private fun adjustDuration(deltaMinutes: Int) {
        if (state.isRunning) return
        val newTotal = (state.totalDurationMillis + deltaMinutes * 60_000L)
            .coerceIn(MIN_DURATION_MILLIS, MAX_DURATION_MILLIS)
        state = state.copy(totalDurationMillis = newTotal, remainingMillis = newTotal)
        persist()
    }

    private fun toggleStartPause() {
        if (state.isRunning) {
            tickerJob?.cancel()
            val remaining = (targetElapsedRealtime - SystemClock.elapsedRealtime()).coerceAtLeast(0L)
            state = state.copy(isRunning = false, remainingMillis = remaining)
        } else {
            if (state.remainingMillis <= 0L) {
                // ✅ time shesh hoye gele abar Start chapley notun kore purota theke shuru
                state = state.copy(remainingMillis = state.totalDurationMillis)
            }
            targetElapsedRealtime = SystemClock.elapsedRealtime() + state.remainingMillis
            state = state.copy(isRunning = true)
            startTicker()
        }
        persist()
    }

    private fun addOneMinute() {
        val extra = 60_000L
        if (state.isRunning) {
            targetElapsedRealtime += extra
        }
        state = state.copy(
            totalDurationMillis = state.totalDurationMillis + extra,
            remainingMillis = state.remainingMillis + extra
        )
        persist()
    }

    private fun reset() {
        tickerJob?.cancel()
        targetElapsedRealtime = 0L
        state = state.copy(remainingMillis = state.totalDurationMillis, isRunning = false)
        persist()
    }

    private fun startTicker() {
        tickerJob?.cancel()
        tickerJob = viewModelScope.launch {
            while (true) {
                val remaining = (targetElapsedRealtime - SystemClock.elapsedRealtime()).coerceAtLeast(0L)
                state = state.copy(remainingMillis = remaining)
                if (remaining <= 0L) {
                    state = state.copy(isRunning = false)
                    persist()
                    break
                }
                delay(50L) // second-precision UI hole 16ms proyojon nei, battery-friendly
            }
        }
    }

    private fun persist() {
        viewModelScope.launch {
            useCases.persistRunState(
                TimerRunState(
                    totalDurationMillis = state.totalDurationMillis,
                    remainingMillis = state.remainingMillis,
                    targetElapsedRealtime = targetElapsedRealtime,
                    isRunning = state.isRunning
                )
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        tickerJob?.cancel()
    }
}