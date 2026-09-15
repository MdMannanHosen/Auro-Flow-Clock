package com.mannanhosen.auraflowclock.data.data_source


import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.mannanhosen.auraflowclock.data.model.StopwatchRunState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.stopwatchDataStore by preferencesDataStore(name = "stopwatch_prefs")

/**
 * Eta hocche stopwatch feature er "10x better" howar sob theke boro karon:
 * Beshirvag alarm/clock app er stopwatch app close/kill hole 0 theke shuru hoye jay.
 * Amra SystemClock.elapsedRealtime() based timestamp DataStore e save kore rakhi,
 * fole app kill hoye abar khullei thik jaiga theke gonona continue hoy - deep sleep,
 * process death, screen off kichutei time hariye jay na.
 */
class StopwatchPreferences @Inject constructor(
    private val context: Context
) {

    private object Keys {
        val IS_RUNNING = booleanPreferencesKey("is_running")
        val BASE_ELAPSED_REALTIME = longPreferencesKey("base_elapsed_realtime")
        val ACCUMULATED_MILLIS = longPreferencesKey("accumulated_millis")
        val SESSION_ID = longPreferencesKey("session_id")
    }

    data class RunState(
        val isRunning: Boolean = false,
        val baseElapsedRealtime: Long = 0L,
        val accumulatedMillis: Long = 0L,
        val sessionId: Long = 0L
    )

    val runState: Flow<RunState> = context.stopwatchDataStore.data.map { prefs ->
        RunState(
            isRunning = prefs[Keys.IS_RUNNING] ?: false,
            baseElapsedRealtime = prefs[Keys.BASE_ELAPSED_REALTIME] ?: 0L,
            accumulatedMillis = prefs[Keys.ACCUMULATED_MILLIS] ?: 0L,
            sessionId = prefs[Keys.SESSION_ID] ?: 0L
        )
    }

    suspend fun saveRunState(state: StopwatchRunState) {
        context.stopwatchDataStore.edit { prefs ->
            prefs[Keys.IS_RUNNING] = state.isRunning
            prefs[Keys.BASE_ELAPSED_REALTIME] = state.baseElapsedRealtime
            prefs[Keys.ACCUMULATED_MILLIS] = state.accumulatedMillis
            prefs[Keys.SESSION_ID] = state.sessionId
        }
    }
}