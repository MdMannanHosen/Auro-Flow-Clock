package com.mannanhosen.auraflowclock.presentation.worldclock
import java.util.Calendar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mannanhosen.auraflowclock.data.model.entity.TimeZone
import com.mannanhosen.auraflowclock.domain.usecase.worldclock.GetAllTimeZonesUseCase
import com.mannanhosen.auraflowclock.domain.usecase.worldclock.GetSelectedTimeZonesUseCase
import com.mannanhosen.auraflowclock.domain.usecase.worldclock.InitializeDefaultTimeZonesUseCase
import com.mannanhosen.auraflowclock.domain.usecase.worldclock.UpdateTimeZoneSelectionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class WorldClockViewModel @Inject constructor(
    private val getAllTimeZonesUseCase: GetAllTimeZonesUseCase,
    private val getSelectedTimeZonesUseCase: GetSelectedTimeZonesUseCase,
    private val updateTimeZoneSelectionUseCase: UpdateTimeZoneSelectionUseCase,
    private val initializeDefaultTimeZonesUseCase: InitializeDefaultTimeZonesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TimeZoneUiState())
    val uiState : StateFlow<TimeZoneUiState> = _uiState.asStateFlow()

   private  val _currentTimes = MutableStateFlow<List<CurrentTimeInfo>>(emptyList())
    val currentTimes : StateFlow<List<CurrentTimeInfo>> = _currentTimes.asStateFlow()

    init {
        viewModelScope.launch {
            initializeData()
            observeTimeZones()
        }
        startTimeUpdates()
    }


    private suspend fun initializeData() {
        try {
            _uiState.value = _uiState.value.copy(isLoading = true)
            initializeDefaultTimeZonesUseCase()
            _uiState.value = _uiState.value.copy(isLoading = false)
        }catch (e: Exception) {
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                error = e.message
            )
        }


    }

    private suspend fun observeTimeZones(){
        combine(
            getAllTimeZonesUseCase(),
            getSelectedTimeZonesUseCase()
        ) { allTimeZones, selectedTimeZones ->

            TimeZoneUiState(
                allTimeZones = allTimeZones,
                selectedTimeZones = selectedTimeZones,
                isLoading = false
            )
        }.catch { throwable ->
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                error = throwable.message
            )
        }.collect { state ->

            _uiState.value = state
            updateCurrentTimes(state.selectedTimeZones)
        }
    }

    private fun startTimeUpdates() {
        viewModelScope.launch {
            while (true) {
            updateCurrentTimes(_uiState.value.selectedTimeZones)
                delay(1000)
            }
        }
    }

    private fun updateCurrentTimes(timeZones: List<TimeZone>) {
        val currentTimeInfos = timeZones.map { timeZone ->
            // Create time zone-specific calendar instance
            val timeZoneObj = java.util.TimeZone.getTimeZone(timeZone.timeZoneName)
            val calendar = Calendar.getInstance(timeZoneObj)

            // Configure formatters with appropriate time zone
            val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
            val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())

            timeFormat.timeZone = timeZoneObj
            dateFormat.timeZone = timeZoneObj

            // Create formatted time information
            CurrentTimeInfo(
                timeZone = timeZone,
                currentTime = timeFormat.format(calendar.time),
                timeFormat = timeFormat.format(calendar.time),
                dateFormat = dateFormat.format(calendar.time)
            )
        }
        _currentTimes.value = currentTimeInfos
    }

    fun toggleTimeZoneSelection(timeZone: TimeZone) {
        viewModelScope.launch {
            try {
                updateTimeZoneSelectionUseCase(
                    timeZone.uid,
                    !timeZone.isSelected
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message)
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

}