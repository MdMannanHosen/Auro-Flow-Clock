package com.mannanhosen.auraflowclock.Data_Kitchen.Local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mannanhosen.auraflowclock.Domain_MenuCard.Model.Alarm
import kotlinx.coroutines.flow.Flow

@Dao
interface AlaramDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun  insert(alarm: Alarm)

    @Delete
    suspend fun delete (alarm: Alarm)

    @Update
    suspend fun update(alarm: Alarm)

    @Query("DELETE FROM Alarm_List_Table")
    suspend fun clear()

    @Query("SELECT * FROM  Alarm_List_Table ORDER BY id DESC")
    fun getAlarmsList() : Flow<List<Alarm>>

    @Query("SELECT * FROM Alarm_List_Table  WHERE id =:id")
    suspend fun getAlarmById(id : Int) : Alarm ?

    @Query("SELECT id FROM Alarm_List_Table ORDER BY id DESC LIMIT 1")
    suspend fun getLastId() : Int?

    @Query("SELECT * FROM Alarm_List_Table WHERE hour = :hour AND minute = :minute AND isRecurring = :recurring")
    fun getAlarmByTime(hour : String, minute : String, recurring : Boolean) : Flow<Alarm?>

}