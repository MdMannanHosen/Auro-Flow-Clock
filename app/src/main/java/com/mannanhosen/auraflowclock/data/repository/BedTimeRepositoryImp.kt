package com.mannanhosen.auraflowclock.data.repository

import com.mannanhosen.auraflowclock.data.data_source.BedTimeDao
import com.mannanhosen.auraflowclock.data.model.entity.BedTime
import com.mannanhosen.auraflowclock.domain.repository.BedTimeRepository
import kotlinx.coroutines.flow.Flow

class BedTimeRepositoryImp(private val bedTimeDao: BedTimeDao) : BedTimeRepository {
    override fun getBedTimes(): Flow<List<BedTime>> {
       return bedTimeDao.getBedTimes()
    }

    override suspend fun getBedTimeById(id: Int?): BedTime? {
       return bedTimeDao.getBedTimeById(id)
    }

    override suspend fun insertBedTime(bedTime: BedTime) {
       return bedTimeDao.insertBedTime(bedTime)
    }

    override suspend fun deleteBedTime(bedTime: BedTime) {
        return bedTimeDao.deleteBedTime(bedTime)
    }
}