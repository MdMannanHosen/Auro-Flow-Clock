package com.mannanhosen.auraflowclock.domain.usecase.worldclock

import com.mannanhosen.auraflowclock.domain.repository.TimeZoneRepository
import javax.inject.Inject

class UpdateTimeZoneSelectionUseCase @Inject constructor(
    private val repository: TimeZoneRepository
) {
    suspend operator fun invoke(uid : String, isSelected : Boolean) {
        repository.updateTimeZoneSelection(uid, isSelected)
    }
}