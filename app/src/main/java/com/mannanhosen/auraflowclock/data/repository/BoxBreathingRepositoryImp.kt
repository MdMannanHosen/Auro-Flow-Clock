package com.mannanhosen.auraflowclock.data.repository
import com.mannanhosen.auraflowclock.data.data_source.BoxBreathingDao
import com.mannanhosen.auraflowclock.data.model.BoxBreathingSettings
import com.mannanhosen.auraflowclock.domain.repository.BoxBreathingRepository
import kotlinx.coroutines.flow.Flow

class BoxBreathingRepositoryImp(
    private val dao: BoxBreathingDao
) : BoxBreathingRepository {

    override val settings: Flow<BoxBreathingSettings?> = dao.getSettings()

    override suspend fun saveSettings(settings: BoxBreathingSettings) = dao.saveSettings(settings)
}
