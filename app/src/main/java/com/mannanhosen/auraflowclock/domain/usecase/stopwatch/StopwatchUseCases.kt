package com.mannanhosen.auraflowclock.domain.usecase.stopwatch

import com.mannanhosen.auraflowclock.data.data_source.StopwatchPreferences
import kotlinx.coroutines.flow.Flow

data class StopwatchUseCases(

    val recordLap: RecordLap,
    val getLaps: GetLaps,
    val clearLaps: ClearLaps,
    val observeRunState: ObserveRunState,
    val persistRunState: PersistRunState,
)