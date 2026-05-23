package com.mannanhosen.auraflowclock.Data_Kitchen.WorkManager.Worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mannanhosen.auraflowclock.Data_Kitchen.Manager.WorkRequestManager
import com.mannanhosen.auraflowclock.Data_Kitchen.Repository.AlarmRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map


@HiltWorker
class AlarmCheckerWorker @AssistedInject constructor(
    @Assisted private val alarmRepository: AlarmRepository,
    @Assisted private val workRequestManager: WorkRequestManager,
    @Assisted ctx : Context,
    @Assisted params : WorkerParameters, ): CoroutineWorker (ctx, params) {
    override suspend fun doWork(): Result {
       return try {
           val scheduleAlarms = alarmRepository.alarmList
               .map { alarmList ->
                   alarmList.filter { alarm ->  alarm.isScheduled}
               }.firstOrNull()

           workRequestManager.cancelWorker(ALARM_CHECKER_TAG)

           Result.success()
       } catch (throwable : Throwable) {
           Result.failure()
       }
    }

}
const val ALARM_CHECKER_TAG = "alarmCheckerTag"