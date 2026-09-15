package com.mannanhosen.auraflowclock.domain.usecase.alarm

import com.mannanhosen.auraflowclock.data.model.entity.Alarm
import com.mannanhosen.auraflowclock.domain.repository.AlarmRepository

class GetAlarm(
   private val  repository: AlarmRepository
) {
     suspend operator fun invoke(id : Int) : Alarm? {
         return repository.getAlarmById(id)
     }

}