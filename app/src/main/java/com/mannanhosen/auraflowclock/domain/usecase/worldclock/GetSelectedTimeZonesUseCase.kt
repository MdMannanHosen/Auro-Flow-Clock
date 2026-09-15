package com.mannanhosen.auraflowclock.domain.usecase.worldclock

import com.mannanhosen.auraflowclock.data.model.entity.TimeZone
import com.mannanhosen.auraflowclock.domain.repository.TimeZoneRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSelectedTimeZonesUseCase @Inject constructor(
    private val repository: TimeZoneRepository
) {
    suspend operator fun invoke() : Flow<List<TimeZone>> = repository.getSelectedTimeZones()
}