package com.mannanhosen.auraflowclock.presentation.worldclock

import com.mannanhosen.auraflowclock.data.model.entity.TimeZone

data class CurrentTimeInfo (
    val timeZone: TimeZone,
    val currentTime: String,
    val timeFormat: String = "HH:mm",
    val dateFormat: String = "MMM dd, yyyy"
)
