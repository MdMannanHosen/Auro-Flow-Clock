package com.mannanhosen.auraflowclock.Data_Kitchen.Repository

import com.mannanhosen.auraflowclock.Data_Kitchen.Local.AlaramDao
import com.mannanhosen.auraflowclock.Domain_MenuCard.Model.Alarm
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class AlarmRepository @Inject constructor(private val alaramDao: AlaramDao) {

   val alarmList = alaramDao.getAlarmsList().distinctUntilChanged()
    suspend fun insert(alarm: Alarm) = alaramDao.insert(alarm)
    suspend fun  getLastId() = alaramDao.getLastId()
    suspend fun getAlarmById(id: Int) = alaramDao.getAlarmById(id)
    fun getAlarmByTime(hour: String, minute : String, recurring : Boolean) =
        alaramDao.getAlarmByTime(hour, minute, recurring)
}