package com.mannanhosen.auraflowclock.presentation.bedtime.viewmodel
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.mannanhosen.auraflowclock.domain.usecase.bedtime.CalculateBedTimesUseCase
import com.mannanhosen.auraflowclock.domain.usecase.bedtime.WeakTimeUseCase
import com.mannanhosen.auraflowclock.presentation.bedtime.bedtimeuistate.BedTimeUiState
import com.mannanhosen.auraflowclock.presentation.bedtime.components.BedTimeMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalTime
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class BedTimeViewModel @Inject constructor(
    private val weakTimeUseCase: WeakTimeUseCase,
    private val calculateBedTimesUseCase: CalculateBedTimesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(BedTimeUiState())
    val uiState: StateFlow<BedTimeUiState> = _uiState.asStateFlow()

    fun onModeChange(mode: BedTimeMode) {
        _uiState.update {
            it.copy(
                mode = mode,
                recommendations = emptyList(),
                selectedTime = getDefaultTimeForMode(mode)
            )
        }
    }

    fun onShowTimePicker(show: Boolean) {
        _uiState.update {
            it.copy(showTimePicker = show)
        }
    }

    suspend fun onTimeSelected(time: LocalTime) {
        _uiState.update {
            it.copy(selectedTime = time, showTimePicker = false)
        }
        onCalculate()
    }

    suspend fun onCalculate() {
        val state = _uiState.value
        val recs = when (state.mode) {
            BedTimeMode.WAKE_UP_TIME -> calculateBedTimesUseCase(
                wakeUpTime = state.selectedTime,
                sleepCycleDurationMinutes = state.sleepCycleLength,
                fallAsleepBufferMinutes = state.fallAsleepBuffer
            )

            BedTimeMode.BED_TIME -> weakTimeUseCase(
                bedTime = state.selectedTime,
                sleepCycleDurationMinutes = state.sleepCycleLength,
                fallAsleepBufferMinutes = state.fallAsleepBuffer
            )
        }

        _uiState.update { it.copy(recommendations = recs) }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDefaultTimeForMode(mode: BedTimeMode): LocalTime {
        return when (mode) {
            BedTimeMode.WAKE_UP_TIME -> LocalTime.of(9, 0)
            BedTimeMode.BED_TIME -> LocalTime.of(23, 0)
        }
    }
}