package com.mannanhosen.auraflowclock.data.data_source
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mannanhosen.auraflowclock.data.model.entity.BedTime
import kotlinx.coroutines.flow.Flow

@Dao
interface BedTimeDao {
    @Query("select *  from bedtime_table")
    fun getBedTimes() : Flow<List<BedTime>>

    @Query("select * from bedtime_table where id =:id")
    suspend fun getBedTimeById(id:Int?): BedTime?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBedTime(bedTime : BedTime)

    @Update
    suspend fun updateBedTime(bedTime : BedTime)

    @Delete
    suspend fun deleteBedTime(bedTime: BedTime)


}