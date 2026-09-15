package com.mannanhosen.auraflowclock.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Stopwatch_Lap_Table")
data class StopwatchLap(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val sessionId: Long,
    val lapNumber: Int,
    val lapTimeMillis: Long,      // ei lap ta koto somoy niyeche (split time - age lap theke koto pore)
    val totalTimeMillis: Long     // stopwatch start howar por theke total kotokkhon
)