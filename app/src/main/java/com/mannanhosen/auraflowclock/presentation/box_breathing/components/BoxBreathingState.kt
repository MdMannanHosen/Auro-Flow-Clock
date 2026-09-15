package com.mannanhosen.auraflowclock.presentation.box_breathing.components

enum class BoxBreathingPhase(val label: String) {
    INHALE("Inhale"),
    HOLD_FULL("Hold"),
    EXHALE("Exhale"),
    HOLD_EMPTY("Hold")
}

data class BoxBreathingState(
    val inhaleSeconds: Int = 0,
    val holdFullSeconds: Int = 0,
    val exhaleSeconds: Int = 0,
    val holdEmptySeconds: Int = 0,
    val sessionDurationSeconds: Int = 5 * 60,

    val isRunning: Boolean = false,
    val currentPhase: BoxBreathingPhase = BoxBreathingPhase.INHALE,
    val phaseSecondsLeft: Int = 0,
    val cycleCount: Int = 0,
    val sessionSecondsElapsed: Int = 0,
    val isSessionComplete: Boolean = false,

    // ✅ notun - Start chapar por 3-2-1 countdown dekhanor jonno
    val isCountingDown: Boolean = false,
    val countdownValue: Int = 3
) {
    fun durationFor(phase: BoxBreathingPhase): Int = when (phase) {
        BoxBreathingPhase.INHALE -> inhaleSeconds
        BoxBreathingPhase.HOLD_FULL -> holdFullSeconds
        BoxBreathingPhase.EXHALE -> exhaleSeconds
        BoxBreathingPhase.HOLD_EMPTY -> holdEmptySeconds
    }

    val phaseProgress: Float
        get() {
            val total = durationFor(currentPhase)
            if (total <= 0) return 1f
            return (1f - phaseSecondsLeft.toFloat() / total.toFloat()).coerceIn(0f, 1f)
        }

    val sessionProgress: Float
        get() = if (sessionDurationSeconds <= 0) 0f
        else (sessionSecondsElapsed.toFloat() / sessionDurationSeconds.toFloat()).coerceIn(0f, 1f)

    /** eta age kokhono start hoyni - fresh idle obostha */
    val isIdle: Boolean
        get() = !isRunning && !isCountingDown && !isSessionComplete &&
                cycleCount == 0 && sessionSecondsElapsed == 0

    /** pause kore rakha ache, session complete hoyni */
    val isPaused: Boolean
        get() = !isRunning && !isCountingDown && !isSessionComplete && !isIdle
}