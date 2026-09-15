package com.mannanhosen.auraflowclock.domain.repository

import com.mannanhosen.auraflowclock.data.model.entity.Alarm
import com.mannanhosen.auraflowclock.data.model.entity.BedTime
import kotlinx.coroutines.flow.Flow

interface BedTimeRepository {
    fun getBedTimes() : Flow<List<BedTime>>
    suspend fun getBedTimeById(id: Int?) : BedTime?
    suspend fun insertBedTime(bedTime: BedTime)
    suspend fun deleteBedTime(bedTime: BedTime)

}