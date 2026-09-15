package com.mannanhosen.auraflowclock.data.model
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Box_Breathing_Settings_Table")
data class BoxBreathingSettings(
    @PrimaryKey
    val id: Int = 0,
    val inhaleSeconds: Int = 4,
    val holdFullSeconds: Int = 4,
    val exhaleSeconds: Int = 4,
    val holdEmptySeconds: Int = 4,
    val sessionDurationSeconds: Int = 5 * 60, // 5 minute default session
    val completedSessions: Int = 0
)
