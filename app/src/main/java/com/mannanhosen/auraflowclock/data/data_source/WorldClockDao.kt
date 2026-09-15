package com.mannanhosen.auraflowclock.data.data_source
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mannanhosen.auraflowclock.data.model.entity.TimeZone
import kotlinx.coroutines.flow.Flow

@Dao
interface TimeZoneDao {

    @Query("SELECT * FROM timezone_table ORDER BY display_name ASC")
    fun getAllTimeZones(): Flow<List<TimeZone>>

    @Query("SELECT * FROM timezone_table WHERE is_selected = 1 ORDER BY display_name ASC")
    fun getSelectTimeZones(): Flow<List<TimeZone>>   // suspend বাদ দেওয়া হয়েছে

    @Query("select * from timezone_table where uid = :uid")
    suspend fun getTimeZoneById(uid: Int): TimeZone

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTimeZones(timeZone: List<TimeZone>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTimeZone(timeZone: TimeZone)

    @Update
    suspend fun updateTimeZone(timeZone: TimeZone)

    @Delete
    suspend fun deleteTimeZone(timeZone: TimeZone)

    @Query("delete from timezone_table")
    suspend fun deleteAllTimeZones()

    @Query("update timezone_table set is_selected = :isSelected where uid = :uid")
    suspend fun updateTimeZoneSelection(uid: String, isSelected: Boolean)

    @Query("select count(*) from timezone_table")
    suspend fun getTimeZoneCount(): Int
}