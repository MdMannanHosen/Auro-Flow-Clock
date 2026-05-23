package com.mannanhosen.auraflowclock.Data_Kitchen.Receiver

import android.annotation.SuppressLint
import androidx.work.impl.utils.ForceStopRunnable
import dagger.hilt.android.AndroidEntryPoint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.mannanhosen.auraflowclock.Data_Kitchen.Manager.ScheduleAlarmManager
import com.mannanhosen.auraflowclock.Data_Kitchen.Manager.WorkRequestManager
import com.mannanhosen.auraflowclock.Data_Kitchen.WorkManager.Worker.RESCHEDULE_ALARM_TAG
import com.mannanhosen.auraflowclock.Data_Kitchen.WorkManager.Worker.RescheduleAlarmWorker
import hilt_aggregated_deps._dagger_hilt_android_internal_lifecycle_DefaultViewModelFactories_ActivityEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject



@AndroidEntryPoint
class AlarmBroadcastReceiver : BroadcastReceiver() {

    @Inject
   lateinit var scheduleAlarmManager: ScheduleAlarmManager
   @Inject
   lateinit var wrokRequestManager: WorkRequestManager

   private val broadcastReceiverScope = CoroutineScope(SupervisorJob())
    @SuppressLint("SuspiciousIndentation")
    override fun onReceive(p0: Context?, p1: Intent?) {
     val pendingResult : PendingResult = goAsync()
        broadcastReceiverScope.launch (Dispatchers.Default) {
            try {
                p1.let { intent ->
                    when(intent?.action) {
                        "android.intent.action.BOOT_COMPLETED" -> {
                            wrokRequestManager.enqueueWorker<RescheduleAlarmWorker>(
                                RESCHEDULE_ALARM_TAG
                            )

                        }

//                        ACTION_DISMISS -> wrokRequestManager.cancelWorker()
                        ACTION_SNOOZE -> {

                        }
                    }
                }
            } finally {

            }
        }
    }
}

const val IS_RECURRING = "IS_RECURRING"
const val DAYS_SELECTED = "DAYS_SELECTED"
const val TITLE = "TITLE"
const val HOUR = "HOUR"
const val MINUTE = "MINUTE"
const val ACTION_DISMISS = "ACTION_DISMISS"
const val ACTION_SNOOZE = "ACTION_SNOOZE"