package com.mannanhosen.auraflowclock.data.database
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mannanhosen.auraflowclock.data.data_source.BedTimeDao
import com.mannanhosen.auraflowclock.data.model.Converters
import com.mannanhosen.auraflowclock.data.model.entity.BedTime

@Database(
    entities = [BedTime:: class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
 abstract  class BedTimeDataBase : RoomDatabase() {
    abstract fun bedTimeDao() : BedTimeDao
    companion object{
        const val DATABASE_NAME = "bedTime_database"
    }
}