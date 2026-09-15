package com.mannanhosen.auraflowclock.data.data_source
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mannanhosen.auraflowclock.data.model.BoxBreathingSettings
import kotlinx.coroutines.flow.Flow

@Dao
interface BoxBreathingDao {

    @Query("SELECT * FROM Box_Breathing_Settings_Table WHERE id = 0")
    fun getSettings(): Flow<BoxBreathingSettings?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSettings(settings: BoxBreathingSettings)
}
