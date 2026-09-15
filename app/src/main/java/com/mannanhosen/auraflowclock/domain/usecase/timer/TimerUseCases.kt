package com.mannanhosen.auraflowclock.domain.usecase.timer


data class TimerUseCases(
    val observeRunState: ObserveTimerRunState,
    val persistRunState: PersistTimerRunState
)