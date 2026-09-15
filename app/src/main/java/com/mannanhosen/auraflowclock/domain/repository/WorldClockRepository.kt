package com.mannanhosen.auraflowclock.domain.repository

import com.mannanhosen.auraflowclock.data.model.entity.TimeZone
import kotlinx.coroutines.flow.Flow

interface TimeZoneRepository {

    fun getAllTimeZones(): Flow<List<TimeZone>>
    suspend fun getSelectedTimeZones(): Flow<List<TimeZone>>
    suspend fun getTimeZoneById(uid: Int): TimeZone?
    suspend fun insertTimeZone(timeZone: TimeZone)
    suspend fun updateTimeZone(timeZone: TimeZone)
    suspend fun deleteAllTimeZones()
    suspend fun updateTimeZoneSelection(uid: String, isSelected: Boolean)
    suspend fun getTimeZoneCount(): Int
    suspend fun initializeDefaultTimeZones()


}
