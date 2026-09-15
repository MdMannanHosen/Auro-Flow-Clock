package com.mannanhosen.auraflowclock.data.data_source
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mannanhosen.auraflowclock.data.model.entity.Alarm
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao {

    @Query("SELECT * FROM  Alarm_List_Table")
    fun getAlarms() : Flow<List<Alarm>>

    @Query ("SELECT * FROM Alarm_List_Table WHERE id =:id")
    suspend fun getAlarmById (id: Int?) : Alarm?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlarm(alarm: Alarm)

    @Update
    suspend fun updateAlarm(alarm: Alarm)
    @Delete
    suspend fun deleteAlarm(alarm: Alarm )




}

