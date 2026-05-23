package com.mannanhosen.auraflowclock.Presentation_Table.alarm

import android.provider.SyncStateContract.Helpers.update
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mannanhosen.auraflowclock.Data_Kitchen.Manager.ScheduleAlarmManager
import com.mannanhosen.auraflowclock.Data_Kitchen.Model.Alarm
import com.mannanhosen.auraflowclock.Data_Kitchen.Repository.AlarmRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.compose.runtime.*
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@HiltViewModel
class AlarmViewModel @Inject constructor(
    private val alarmRepository: AlarmRepository,
    private val scheduleAlarmManager: ScheduleAlarmManager
)  : ViewModel(), AlarmActions{

    val alarmsLisState = alarmRepository.alarmList.asLiveData()
    var alarmCreationState by mutableStateOf(Alarm())
    override fun updateAlarmCreationState(alarm: Alarm) {
       alarm.setDaysSelected(alarm.daysSelected)
        alarmCreationState = alarm
    }

    override fun save () {
        viewModelScope.launch {
            val lastId = alarmRepository.getLastId()
            val alarm = alarmRepository.getAlarmById(alarmCreationState.id)

            if(!alarmCreationState.isScheduled) {
                alarmCreationState.isScheduled = true
            }

            listOf(
                async {
                    if (alarm?.id ==alarmCreationState.id) {
//                        update(alarmCreationState)
                    }

//                    async {
//                        if (alarm.isScheduled) {
//                        scheduleAlarmManager.schedule(alarm)
//                        }
//                    }
                }
            )
        }
    }
}