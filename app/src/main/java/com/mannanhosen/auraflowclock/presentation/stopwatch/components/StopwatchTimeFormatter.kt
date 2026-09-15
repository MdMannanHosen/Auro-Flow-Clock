package com.mannanhosen.auraflowclock.presentation.stopwatch.components


import java.util.Locale

object StopwatchTimeFormatter {

    /** 1 ghonta er kom hole "mm:ss.cc", beshi hole "hh:mm:ss.cc" */
    fun format(millis: Long): String {
        val totalCentis = millis / 10
        val centis = totalCentis % 100
        val totalSeconds = totalCentis / 100
        val seconds = totalSeconds % 60
        val totalMinutes = totalSeconds / 60
        val minutes = totalMinutes % 60
        val hours = totalMinutes / 60

        return if (hours > 0) {
            String.format(Locale.US, "%02d:%02d:%02d.%02d", hours, minutes, seconds, centis)
        } else {
            String.format(Locale.US, "%02d:%02d.%02d", minutes, seconds, centis)
        }
    }
}