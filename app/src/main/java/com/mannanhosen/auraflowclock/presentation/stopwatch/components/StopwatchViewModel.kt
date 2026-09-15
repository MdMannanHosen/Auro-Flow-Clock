package com.mannanhosen.auraflowclock.presentation.stopwatch.components


import android.os.SystemClock
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mannanhosen.auraflowclock.data.model.StopwatchRunState
import com.mannanhosen.auraflowclock.domain.usecase.stopwatch.StopwatchUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StopwatchViewModel @Inject constructor(
    private val useCases: StopwatchUseCases
) : ViewModel() {

    var state by mutableStateOf(StopwatchState())
        private set

    // ⏱️ base = SystemClock.elapsedRealtime() যখন শেষবার Start চাপা হয়েছে।
    // SystemClock.elapsedRealtime() ব্যবহার করা হচ্ছে কারণ এটি deep sleep / doze mode
    // এও ঠিক থাকে, wall-clock (System.currentTimeMillis) এর মতো user time change
    // করলে এগিয়ে/পিছিয়ে যায় না।
    private var baseElapsedRealtime = 0L
    private var accumulatedMillis = 0L   // pause অবস্থায় যোগ করা total সময়
    private var sessionId = 0L

    private var tickerJob: Job? = null

    init {
        viewModelScope.launch {
            // 🔁 App process kill হয়ে user আবার screen এ ফিরে আসলে, এই জায়গাটা থেকে
            // stopwatch ঠিক আগের অবস্থায় ফিরে আসবে - 0 থেকে শুরু হবে না।
            // Room এ প্রথম বার কোনো row না থাকলে null আসবে, তাই default state ব্যবহার করা হলো।
            val saved = useCases.observeRunState().first() ?: StopwatchRunState()
            baseElapsedRealtime = saved.baseElapsedRealtime
            accumulatedMillis = saved.accumulatedMillis
            sessionId = if (saved.sessionId == 0L) System.currentTimeMillis() else saved.sessionId

            state = state.copy(isRunning = saved.isRunning)
            state = state.copy(elapsedMillis = currentElapsed())

            if (saved.isRunning) startTicker()

            observeLaps()
        }
    }

    private fun observeLaps() {
        viewModelScope.launch {
            useCases.getLaps(sessionId).collect { laps ->
                // ✅ best (সবচেয়ে দ্রুত) ও worst (সবচেয়ে ধীর) lap বের করা,
                // Google Clock এর মতো color highlight এর জন্য - 1 টার বেশি lap থাকলে
                val best = laps.minByOrNull { it.lapTimeMillis }?.id
                val worst = laps.maxByOrNull { it.lapTimeMillis }?.id
                state = state.copy(
                    laps = laps,
                    bestLapId = if (laps.size > 1) best else null,
                    worstLapId = if (laps.size > 1) worst else null
                )
            }
        }
    }

    private fun currentElapsed(): Long {
        return if (state.isRunning) {
            accumulatedMillis + (SystemClock.elapsedRealtime() - baseElapsedRealtime)
        } else {
            accumulatedMillis
        }
    }

    fun onEvent(event: StopwatchEvent) {
        when (event) {
            is StopwatchEvent.StartPause -> toggleStartPause()
            is StopwatchEvent.Lap -> recordLap()
            is StopwatchEvent.Reset -> reset()
        }
    }

    private fun toggleStartPause() {
        if (state.isRunning) {
            // ⏸️ Pause: এখন পর্যন্ত জমানো total সময় freeze করে রাখা
            accumulatedMillis = currentElapsed()
            tickerJob?.cancel()
            state = state.copy(isRunning = false, elapsedMillis = accumulatedMillis)
        } else {
            // ▶️ Start: fresh state থেকে শুরু করলে নতুন session শুরু
            if (accumulatedMillis == 0L && state.laps.isEmpty()) {
                sessionId = System.currentTimeMillis()
            }
            baseElapsedRealtime = SystemClock.elapsedRealtime()
            state = state.copy(isRunning = true)
            startTicker()
            observeLaps()
        }
        persistRunState()
    }

    private fun startTicker() {
        tickerJob?.cancel()
        tickerJob = viewModelScope.launch {
            while (true) {
                state = state.copy(elapsedMillis = currentElapsed())
                delay(16L) // ~60fps smooth UI update, সময় দেখানো হয় centisecond precision এ
            }
        }
    }

    private fun recordLap() {
        if (!state.isRunning) return
        viewModelScope.launch {
            val previousTotal = state.laps.maxOfOrNull { it.totalTimeMillis } ?: 0L
            useCases.recordLap(
                sessionId = sessionId,
                lapNumber = state.laps.size + 1,
                totalTimeMillis = state.elapsedMillis,
                previousTotalMillis = previousTotal
            )
        }
    }

    private fun reset() {
        tickerJob?.cancel()
        val sessionToClear = sessionId
        viewModelScope.launch {
            useCases.clearLaps(sessionToClear)
        }
        baseElapsedRealtime = 0L
        accumulatedMillis = 0L
        sessionId = System.currentTimeMillis()
        state = state.copy(
            isRunning = false,
            elapsedMillis = 0L,
            laps = emptyList(),
            bestLapId = null,
            worstLapId = null
        )
        persistRunState()
        observeLaps()
    }

    private fun persistRunState() {
        viewModelScope.launch {
            useCases.persistRunState(
                StopwatchRunState(
                    isRunning = state.isRunning,
                    baseElapsedRealtime = baseElapsedRealtime,
                    accumulatedMillis = accumulatedMillis,
                    sessionId = sessionId
                )
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        tickerJob?.cancel()
    }
}