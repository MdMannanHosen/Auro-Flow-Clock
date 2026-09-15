
package com.mannanhosen.auraflowclock.domain.usecase.bedtime

import android.os.Build
import androidx.annotation.RequiresApi
import com.mannanhosen.auraflowclock.data.model.entity.BedTime
import java.time.LocalTime

class CalculateBedTimesUseCase {
    @RequiresApi(Build.VERSION_CODES.O)
    operator fun invoke(
        wakeUpTime : LocalTime,
        sleepCycleDurationMinutes : Int,
        fallAsleepBufferMinutes : Int
    ) : List<BedTime> {
        return (6 downTo 1).map { cycles ->
            val totalSleepMinutes = cycles * sleepCycleDurationMinutes + fallAsleepBufferMinutes
            val recommendedTime = wakeUpTime.minusMinutes(totalSleepMinutes.toLong())

            BedTime(cycles, totalSleepMinutes, recommendedTime)
        }
    }
}