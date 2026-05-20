package com.mannanhosen.auraflowclock.Data_Kitchen.WorkManager.Worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mannanhosen.auraflowclock.Data_Kitchen.Manager.ScheduleAlarmManager
import com.mannanhosen.auraflowclock.Data_Kitchen.Manager.WorkRequestManager
import com.mannanhosen.auraflowclock.Data_Kitchen.Repository.AlarmRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

// @HiltWorker: Ei class e jeno Hilt diye dependency injection kora jay, tar permission dicche.
@HiltWorker
class RescheduleAlarmWorker @AssistedInject constructor(

    // 1. Almarituku theke neya jinish (Hilt nije ene debe): Tai egulor age @Assisted boseni.
    private val alarmRepository: AlarmRepository,          // Alarm er list ba khata
    private val scheduleAlarmManager: ScheduleAlarmManager,  // Alarm on/off korar mike ba speaker
    private val workRequestManager: WorkRequestManager,      // Ei background kajta control korar head sir

    // 2. OS theke pathano jinish (WorkManager hate dhoriye debe): Tai egulor age @Assisted boseche.
    @Assisted ctx: Context,                                  // App er onumoti potro ba context
    @Assisted params: WorkerParameters,                      // Worker er nijesso niyomkanun ba parameters
) : CoroutineWorker(ctx, params) { // CoroutineWorker ke extend kora hoyeche background e code run korar jonno

    // doWork(): Ei function er vetor i আসল background er kajta running hoy
    override suspend fun doWork(): Result {
        return try {


            Result.success()
        } catch (throwable: Throwable) {
            // Jodi kono karone code e bhul ba error ase, tobe 'Failure' signal pathano hoy
            Result.failure()
        }
    }
}

// Ei worker ba background task tir ekti unique nam (Tag), ja diye etike chena o bondho kora jay
const val RESCHEDULE_ALARM_TAG = "re scheduleAlarmTag"