package com.mannanhosen.auraflowclock.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mannanhosen.auraflowclock.data.data_source.AlarmDao
import com.mannanhosen.auraflowclock.data.data_source.BoxBreathingDao
import com.mannanhosen.auraflowclock.data.data_source.StopwatchDao
import com.mannanhosen.auraflowclock.data.data_source.TimerDao
import com.mannanhosen.auraflowclock.data.model.BoxBreathingSettings
import com.mannanhosen.auraflowclock.data.model.Converters
import com.mannanhosen.auraflowclock.data.model.StopwatchLap
import com.mannanhosen.auraflowclock.data.model.StopwatchRunState
import com.mannanhosen.auraflowclock.data.model.TimerRunState
import com.mannanhosen.auraflowclock.data.model.entity.Alarm

@Database(
    entities = [
        Alarm::class,
        StopwatchLap::class,
        StopwatchRunState::class,
        TimerRunState::class,
        BoxBreathingSettings::class,
               ],  // ⬅️ notun 2 ta jog
    version = 12,   // ⬅️ 9 theke 10
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AlarmDataBase : RoomDatabase() {
    abstract val alarmDao: AlarmDao
    abstract val timerDao : TimerDao
    abstract val stopwatchDao: StopwatchDao
    abstract val boxBreathingDao: BoxBreathingDao
    companion object {
        const val DATABASE_NAME = "alarm_db"
    }
}