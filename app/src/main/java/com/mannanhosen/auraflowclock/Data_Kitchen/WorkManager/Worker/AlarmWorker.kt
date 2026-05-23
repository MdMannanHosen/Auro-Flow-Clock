package com.mannanhosen.auraflowclock.Data_Kitchen.WorkManager.Worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.WorkerParameters
import com.mannanhosen.auraflowclock.Data_Kitchen.Manager.WorkRequestManager
import com.mannanhosen.auraflowclock.Data_Kitchen.Repository.AlarmRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class AlarmWorker  @AssistedInject constructor(
    @Assisted private val alarmRepository: AlarmRepository,
    @Assisted private val alarmNo: WorkRequestManager,
    appContext: Context,
    params: WorkerParameters
)


val alarmTag = "ALARM_TAG"