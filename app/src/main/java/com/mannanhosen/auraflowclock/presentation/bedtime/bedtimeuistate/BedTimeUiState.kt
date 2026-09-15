package com.mannanhosen.auraflowclock.presentation.bedtime.bedtimeuistate

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import com.mannanhosen.auraflowclock.data.model.entity.BedTime
import com.mannanhosen.auraflowclock.presentation.bedtime.components.BedTimeMode
import java.time.LocalTime
@RequiresApi(Build.VERSION_CODES.O)
data class BedTimeUiState (
    @SuppressLint("NewApi")
    val mode : BedTimeMode = BedTimeMode.WAKE_UP_TIME,
     val selectedTime : LocalTime = if(mode == BedTimeMode.WAKE_UP_TIME){
        LocalTime.of(9,0)
    }else{
        LocalTime.of(23,0)
    },
  val recommendations : List<BedTime> = emptyList(),
  val showTimePicker: Boolean = false,

    val is24HourFormat: Boolean = false,
    val fallAsleepBuffer: Int = 15,
    val sleepCycleLength: Int = 90


)
