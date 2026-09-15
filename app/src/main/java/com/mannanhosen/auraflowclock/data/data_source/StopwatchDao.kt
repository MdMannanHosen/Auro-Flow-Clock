package com.mannanhosen.auraflowclock.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mannanhosen.auraflowclock.data.model.StopwatchLap
import com.mannanhosen.auraflowclock.data.model.StopwatchRunState
import kotlinx.coroutines.flow.Flow

@Dao
interface StopwatchDao {

    @Query("SELECT * FROM Stopwatch_Lap_Table WHERE sessionId =:sessionId ORDER BY lapNumber DESC")
    fun getLapsForSession(sessionId: Long) : Flow<List<StopwatchLap>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLap(lap: StopwatchLap): Long

    @Query("DELETE FROM Stopwatch_Lap_Table WHERE sessionId =:sessionId")
    suspend fun clearLapsForSession(sessionId: Long)

    @Query("DELETE FROM Stopwatch_Lap_Table")
    suspend fun clearAllLaps()

    // ✅ notun jog kora holo - run state (isRunning/elapsed) ekhon Room-e save hoy,
    //    DataStore lagbe na
    @Query("SELECT * FROM Stopwatch_Run_State_Table WHERE id = 0")
    fun getRunState(): Flow<StopwatchRunState?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveRunState(state: StopwatchRunState)
}