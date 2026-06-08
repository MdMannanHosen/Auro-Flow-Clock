package com.mannanhosen.auraflowclock.Data_Kitchen.Local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mannanhosen.auraflowclock.Domain_MenuCard.Model.Alarm


@Database(entities = [Alarm:: class], version = 1, exportSchema = false)
 abstract class AlarmDataBase: RoomDatabase() {
    abstract fun getAlarmDao() : AlaramDao
}