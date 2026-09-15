package com.mannanhosen.auraflowclock.data.model


import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Timer-er running state - shudhu 1-ta row (id = 0 fixed), stopwatch-er run-state er
 * moto pattern. targetElapsedRealtime = SystemClock.elapsedRealtime() + remaining,
 * jokhon timer chalu hoy - ei jonno app kill/deep sleep hoyeo remaining time thik thake.
 */
@Entity(tableName = "Timer_Run_State_Table")
data class TimerRunState(
    @PrimaryKey
    val id: Int = 0,
    val totalDurationMillis: Long = 5 * 60_000L,
    val remainingMillis: Long = 5 * 60_000L,
    val targetElapsedRealtime: Long = 0L,
    val isRunning: Boolean = false
)