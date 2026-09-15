package com.mannanhosen.auraflowclock.data.model


import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Stopwatch akhon cholche kina - eta memory te na rekhe Room-e rakha hocche.
 * Shobshomoy id = 0 fixed rakha hoyeche, tai eta 1-tai row hishebe thake -
 * notun kore save korle purono row REPLACE hoye jay (DataStore/SharedPreferences
 * er dorkar nei, sob Room-e).
 */
@Entity(tableName = "Stopwatch_Run_State_Table")
data class StopwatchRunState(
    @PrimaryKey
    val id: Int = 0,
    val isRunning: Boolean = false,
    val baseElapsedRealtime: Long = 0L,
    val accumulatedMillis: Long = 0L,
    val sessionId: Long = 0L
)