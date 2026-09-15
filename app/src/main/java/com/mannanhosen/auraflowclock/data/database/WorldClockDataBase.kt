package com.mannanhosen.auraflowclock.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mannanhosen.auraflowclock.data.data_source.TimeZoneDao
import com.mannanhosen.auraflowclock.data.model.entity.TimeZone

@Database(
    entities = [TimeZone :: class],
    version = 1,
    exportSchema = false
)
abstract  class TimeZoneDataBase : RoomDatabase() {
    abstract fun timeZoneDao() : TimeZoneDao
    companion object {
        const val DATABASE_NAME = "timezone_database"
    }
}