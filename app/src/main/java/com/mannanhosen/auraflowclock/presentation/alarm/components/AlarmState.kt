package com.mannanhosen.auraflowclock.presentation.alarm.components

import com.mannanhosen.auraflowclock.data.model.entity.Alarm

data class AlarmState (
  val alarm : List<Alarm> = emptyList()
)
