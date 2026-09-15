package com.mannanhosen.auraflowclock.domain.usecase.bedtime

import com.mannanhosen.auraflowclock.data.model.entity.BedTime
import com.mannanhosen.auraflowclock.domain.repository.BedTimeRepository

class DeleteBedTimeUseCase(
    private val repository: BedTimeRepository) {
    suspend operator fun invoke(bedTime: BedTime){
        repository.deleteBedTime(bedTime)
    }
}