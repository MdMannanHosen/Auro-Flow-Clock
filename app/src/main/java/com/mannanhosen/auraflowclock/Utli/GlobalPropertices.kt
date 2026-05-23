package com.mannanhosen.auraflowclock.Utli

import android.app.PendingIntent
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

object   GlobalPropertices {

   const val Time_Formate = "%02d:%02d:%02d"

    @RequiresApi(Build.VERSION_CODES.O)
    val dateTimeFormatter : DateTimeFormatter = DateTimeFormatter.ofPattern("EEE,MMMddd")
    @RequiresApi(Build.VERSION_CODES.O)
    val nextDay : LocalDateTime = LocalDateTime.now().plus(1, ChronoUnit.DAYS)
    const val pendingIntentFlags = PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
}