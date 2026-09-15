package com.mannanhosen.auraflowclock.presentation.timer.components


import java.util.Locale

object TimerTimeFormatter {

    /** round-up kora hoy jate shesh second-e "00:00" na dekhiye "00:01" dekhay */
    fun format(millis: Long): String {
        val totalSeconds = (millis + 999) / 1000
        val seconds = totalSeconds % 60
        val totalMinutes = totalSeconds / 60
        val minutes = totalMinutes % 60
        val hours = totalMinutes / 60

        return if (hours > 0) {
            String.format(Locale.US, "%d:%02d:%02d", hours, minutes, seconds)
        } else {
            String.format(Locale.US, "%02d:%02d", minutes, seconds)
        }
    }
}