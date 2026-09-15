package com.mannanhosen.auraflowclock.data.repository
import com.mannanhosen.auraflowclock.data.data_source.AlarmDao
import com.mannanhosen.auraflowclock.data.model.entity.Alarm
import com.mannanhosen.auraflowclock.data.model.ChallengeType
import com.mannanhosen.auraflowclock.domain.repository.AlarmRepository
import kotlinx.coroutines.flow.Flow

class AlarmRepositoryImp(private val alarmDao: AlarmDao) : AlarmRepository {
    override fun getAlarms(): Flow<List<Alarm>> {
        return alarmDao.getAlarms()
    }

    override suspend fun getAlarmById(id: Int?): Alarm? {
     return alarmDao.getAlarmById(id)
    }

    override suspend fun insertAlarm(alarm: Alarm) {
        alarmDao.insertAlarm(alarm)   // ✅ আগে missing ছিল
    }

    override suspend fun deleteAlarm(alarm: Alarm) {
        alarmDao.deleteAlarm(alarm)   // ✅ আগে missing ছিল
    }

    override suspend fun updateChallengeType(alarmId: Int, type: ChallengeType, qrValue: String?) {
        val alarm = alarmDao.getAlarmById(alarmId) ?: return
        alarmDao.updateAlarm(
            alarm.copy(challengeType = type, qrTargetValue = qrValue)
        )
    }


}