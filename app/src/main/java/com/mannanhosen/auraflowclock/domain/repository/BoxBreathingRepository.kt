package com.mannanhosen.auraflowclock.domain.repository
import com.mannanhosen.auraflowclock.data.model.BoxBreathingSettings
import kotlinx.coroutines.flow.Flow

interface BoxBreathingRepository {
    val settings: Flow<BoxBreathingSettings?>
    suspend fun saveSettings(settings: BoxBreathingSettings)
}
