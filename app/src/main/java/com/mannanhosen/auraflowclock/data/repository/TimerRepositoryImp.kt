package com.mannanhosen.auraflowclock.data.repository


import com.mannanhosen.auraflowclock.data.data_source.TimerDao
import com.mannanhosen.auraflowclock.data.model.TimerRunState
import com.mannanhosen.auraflowclock.domain.repository.TimerRepository
import kotlinx.coroutines.flow.Flow

class TimerRepositoryImp(
    private val dao: TimerDao
) : TimerRepository {

    override val runState: Flow<TimerRunState?> = dao.getRunState()

    override suspend fun saveRunState(state: TimerRunState) = dao.saveRunState(state)
}