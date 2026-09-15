package com.mannanhosen.auraflowclock.data.repository

import com.mannanhosen.auraflowclock.data.data_source.StopwatchDao
import com.mannanhosen.auraflowclock.data.model.StopwatchLap
import com.mannanhosen.auraflowclock.data.model.StopwatchRunState
import com.mannanhosen.auraflowclock.domain.repository.StopwatchRepository
import kotlinx.coroutines.flow.Flow

class StopwatchRepositoryImp(
    private val dao: StopwatchDao
) : StopwatchRepository {

    override fun getLapsForSession(sessionId: Long): Flow<List<StopwatchLap>> =
        dao.getLapsForSession(sessionId)

    override suspend fun insertLap(lap: StopwatchLap): Long = dao.insertLap(lap)

    override suspend fun clearLapsForSession(sessionId: Long) = dao.clearLapsForSession(sessionId)

    override suspend fun clearAllLaps() = dao.clearAllLaps()

    override val runState: Flow<StopwatchRunState?> = dao.getRunState()

    override suspend fun saveRunState(state: StopwatchRunState) = dao.saveRunState(state)
}