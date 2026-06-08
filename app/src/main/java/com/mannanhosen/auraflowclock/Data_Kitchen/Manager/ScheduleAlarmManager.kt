package com.mannanhosen.auraflowclock.Data_Kitchen.Manager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar

import android.widget.Toast
import com.mannanhosen.auraflowclock.Domain_MenuCard.Model.Alarm
import com.mannanhosen.auraflowclock.Data_Kitchen.Receiver.AlarmBroadcastReceiver
import com.mannanhosen.auraflowclock.Data_Kitchen.Receiver.DAYS_SELECTED
import com.mannanhosen.auraflowclock.Data_Kitchen.Receiver.HOUR
import com.mannanhosen.auraflowclock.Data_Kitchen.Receiver.IS_RECURRING
import com.mannanhosen.auraflowclock.Data_Kitchen.Receiver.MINUTE
import com.mannanhosen.auraflowclock.Data_Kitchen.Receiver.TITLE
import com.mannanhosen.auraflowclock.Data_Kitchen.WorkManager.Worker.ALARM_CHECKER_TAG
import com.mannanhosen.auraflowclock.Data_Kitchen.WorkManager.Worker.AlarmCheckerWorker
import com.mannanhosen.auraflowclock.Utli.GlobalPropertices.pendingIntentFlags
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScheduleAlarmManager @Inject constructor(
    @ApplicationContext private val applicationContext: Context,
    private val workRequestManager: WorkRequestManager,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO //সিস্টেম সার্ভিস ও I/O কাজের জন্য বেশি থ্রেড সাপোর্ট করে, তাই এটি ব্যবহার করা হয়েছে।
) {
    private val scope = CoroutineScope(Dispatchers.Main)
   suspend fun schedule(alarm: Alarm)  = withContext(ioDispatcher) {
val  alarmManager = applicationContext.getSystemService(Context.ALARM_SERVICE) as  AlarmManager
 val alarmIntent = Intent(applicationContext, AlarmBroadcastReceiver::class.java ).apply {

     putExtra(IS_RECURRING, alarm.isRecurring)
     putExtra(MINUTE, alarm.minute)
     putExtra(TITLE, alarm.title)
     putExtra(HOUR, alarm.title)
     putExtra(DAYS_SELECTED, HashMap(alarm.daysSelected))

 }
  val alarmPandingIntent = PendingIntent.getBroadcast(
      applicationContext,
      alarm.id,
      alarmIntent,
      pendingIntentFlags
  )

       val calender = Calendar.getInstance().apply {
           set(Calendar.HOUR_OF_DAY, alarm.hour.toInt())
           set(Calendar.MINUTE, alarm.minute.toInt())
           set(Calendar.SECOND, 0)
           set(Calendar.MILLISECOND, 0)
           if( timeInMillis <= System.currentTimeMillis()) {
               alarm




           }
       }

       val toastText = if(alarm.isRecurring) {
           "Recurring Alarm ${alarm.title} scheduled for ${alarm.hour} : ${alarm.minute}"
       } else {
           "One Time Alarm ${alarm.title} scheduled for at ${alarm.hour}:${alarm.minute}"

       }


    scope.launch {
        Toast.makeText(applicationContext, toastText, Toast.LENGTH_LONG).show() }

       workRequestManager.enqueueWorker<AlarmCheckerWorker>(ALARM_CHECKER_TAG)
       if (alarm.isRecurring) {
           alarmManager.setRepeating(
               AlarmManager.RTC_WAKEUP,
               calender.timeInMillis,
               AlarmManager.INTERVAL_DAY,
               alarmPandingIntent
           )
       } else {
           alarmManager.setExactAndAllowWhileIdle(
               AlarmManager.RTC_WAKEUP,
               calender.timeInMillis,
               alarmPandingIntent
           )
       }
   }



}






