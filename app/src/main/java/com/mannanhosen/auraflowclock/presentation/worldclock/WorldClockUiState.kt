package com.mannanhosen.auraflowclock.presentation.worldclock

import com.mannanhosen.auraflowclock.data.model.entity.TimeZone

data class TimeZoneUiState (
    val allTimeZones: List<TimeZone> = emptyList(),
    val selectedTimeZones: List<TimeZone> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
