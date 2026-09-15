package com.mannanhosen.auraflowclock.domain.usecase.box_breathing


import com.mannanhosen.auraflowclock.data.model.BoxBreathingSettings
import com.mannanhosen.auraflowclock.domain.repository.BoxBreathingRepository
import kotlinx.coroutines.flow.Flow

class ObserveBoxBreathingSettings(
    private val repository: BoxBreathingRepository
) {
    operator fun invoke(): Flow<BoxBreathingSettings?> = repository.settings
}
