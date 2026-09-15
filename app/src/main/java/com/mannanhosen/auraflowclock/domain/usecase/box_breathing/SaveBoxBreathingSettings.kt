package com.mannanhosen.auraflowclock.domain.usecase.box_breathing


import com.mannanhosen.auraflowclock.data.model.BoxBreathingSettings
import com.mannanhosen.auraflowclock.domain.repository.BoxBreathingRepository

class SaveBoxBreathingSettings(
    private val repository: BoxBreathingRepository
) {
    suspend operator fun invoke(settings: BoxBreathingSettings) {
        repository.saveSettings(settings)
    }
}
