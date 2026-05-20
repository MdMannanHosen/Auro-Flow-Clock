package com.mannanhosen.auraflowclock.Data_Kitchen.Manager

import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.compose.ui.unit.Constraints
import com.mannanhosen.auraflowclock.Data_Kitchen.Model.Alarm
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScheduleAlarmManager @Inject constructor(
    @ApplicationContext private val applicationContext: Context,
    private val workRequestManager: WorkRequestManager,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    private val alarmManager = applicationContext.getSystemService(Context.ALARM_SERVICE) as  AlarmManager


  }