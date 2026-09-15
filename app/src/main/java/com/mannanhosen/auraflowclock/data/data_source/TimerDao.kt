package com.mannanhosen.auraflowclock.data.data_source


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mannanhosen.auraflowclock.data.model.TimerRunState
import kotlinx.coroutines.flow.Flow

@Dao
interface TimerDao {

    @Query("SELECT * FROM Timer_Run_State_Table WHERE id = 0")
    fun getRunState(): Flow<TimerRunState?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveRunState(state: TimerRunState)
}