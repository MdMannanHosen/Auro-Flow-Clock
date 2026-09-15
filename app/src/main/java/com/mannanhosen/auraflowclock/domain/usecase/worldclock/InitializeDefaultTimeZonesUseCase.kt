package com.mannanhosen.auraflowclock.domain.usecase.worldclock

import com.mannanhosen.auraflowclock.domain.repository.TimeZoneRepository
import javax.inject.Inject

class InitializeDefaultTimeZonesUseCase @Inject constructor(
    private val repository: TimeZoneRepository
) {
    suspend operator fun invoke() {
        repository.initializeDefaultTimeZones()
    }
}