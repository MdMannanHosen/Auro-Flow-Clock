package com.mannanhosen.auraflowclock.domain.usecase.bedtime

import com.mannanhosen.auraflowclock.data.model.entity.BedTime
import com.mannanhosen.auraflowclock.domain.repository.BedTimeRepository

class GetBedTimeUseCase(
    private val repository: BedTimeRepository
) {
    suspend operator  fun invoke(id: Int): BedTime? {
        return repository.getBedTimeById(id)
    }
}