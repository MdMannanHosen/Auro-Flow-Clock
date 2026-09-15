package com.mannanhosen.auraflowclock.domain.usecase.bedtime

import android.os.Build
import androidx.annotation.RequiresApi
import com.mannanhosen.auraflowclock.data.model.entity.BedTime
import java.time.LocalTime

class WeakTimeUseCase {
    @RequiresApi(Build.VERSION_CODES.O)
    operator fun invoke(
        bedTime: LocalTime,
        sleepCycleDurationMinutes: Int,
        fallAsleepBufferMinutes: Int,
    ): List<BedTime>{

        return (6 downTo 1).map{cycle ->
       val totalSleepMinute = cycle * sleepCycleDurationMinutes + fallAsleepBufferMinutes
       val recommendedTime = bedTime.plusMinutes(totalSleepMinute.toLong())
            BedTime(cycle, totalSleepMinute, recommendedTime)

        }


    }
}
