package com.mannanhosen.auraflowclock.domain.repository

import com.mannanhosen.auraflowclock.data.model.entity.Alarm
import com.mannanhosen.auraflowclock.data.model.ChallengeType
import kotlinx.coroutines.flow.Flow


interface AlarmRepository  {

    fun getAlarms () : Flow<List<Alarm>>
    suspend fun getAlarmById(id: Int?) : Alarm?
    suspend fun insertAlarm(alarm: Alarm)
    suspend fun deleteAlarm(alarm: Alarm)

    suspend fun updateChallengeType(alarmId : Int, type : ChallengeType, qrValue: String? = null)
}