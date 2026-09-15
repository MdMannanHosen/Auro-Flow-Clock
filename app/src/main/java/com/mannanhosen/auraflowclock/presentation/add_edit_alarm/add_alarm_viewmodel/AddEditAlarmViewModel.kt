package com.mannanhosen.auraflowclock.presentation.add_edit_alarm.add_alarm_viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mannanhosen.auraflowclock.data.model.ChallengeType
import com.mannanhosen.auraflowclock.data.model.entity.Alarm
import com.mannanhosen.auraflowclock.data.model.entity.InvalidAlarmException
import com.mannanhosen.auraflowclock.domain.usecase.alarm.AlarmUseCases
import com.mannanhosen.auraflowclock.presentation.add_edit_alarm.add_edit_ui_state.AlarmAddEditState
import com.mannanhosen.auraflowclock.presentation.add_edit_alarm.componentes.AddEditAlarmEvent
import com.mannanhosen.auraflowclock.presentation.alarm.components.AlarmState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@SuppressLint("NewApi")
@HiltViewModel
class AddEditAlarmViewModel @Inject constructor(
    private val alarmUseCases: AlarmUseCases,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _selectedDays = mutableStateOf<Date?>(null)
    val selectDays: State<Date?> = _selectedDays

    private val _alarmTitle = mutableStateOf(AlarmAddEditState("Enter Alarm Name"))
    val alarmTitle: State<AlarmAddEditState> = _alarmTitle

    private val _pickerState = mutableStateOf(AlarmAddEditState())
    val pickerState : State<AlarmAddEditState> = _pickerState

    private val _challengeType = mutableStateOf(ChallengeType.DEFAULT)
    val challengeType: State<ChallengeType> = _challengeType

    private val _weekDays = mutableStateOf(AlarmAddEditState())
    val weekDays : State<AlarmAddEditState> = _weekDays

    private val _ringtoneTitle = mutableStateOf("Default")
    val ringtoneTitle: State<String> = _ringtoneTitle

    private val _ringtoneUri = mutableStateOf<String?>(null)
    val ringtoneUri: State<String?> = _ringtoneUri

    private val _isVibrationEnabled = mutableStateOf(true)
    val isVibrationEnabled: State<Boolean> = _isVibrationEnabled

    private val _snoozeDuration = mutableStateOf(5)
    val snoozeDuration: State<Int> = _snoozeDuration

    private val _snoozeCount = mutableStateOf(3)
    val snoozeCount: State<Int> = _snoozeCount

    private val _containerColor = mutableStateOf(Alarm.Companion.containerColor)
    val containerColor: MutableState<List<Color>> = _containerColor

    private val _state = mutableStateOf(AlarmState())
    val state: State<AlarmState> = _state

    private val _toggleButtonColor = mutableStateOf(Alarm.Companion.containerColor)
    val toggleButtonColor: MutableState<List<Color>> = _toggleButtonColor

    private val _eventFlow = MutableSharedFlow<uiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _vibrationPattern = mutableStateOf("Short Pulse")
    val vibrationPattern: State<String> = _vibrationPattern
    private var currentAlarmId: Int? = null

    fun onDateSelected(date: Date) {
        _selectedDays.value = date
    }


    init {
        loadAlarms()
    }

    init {
        savedStateHandle.get<Int>("alarmId")?.let { alarmid ->
            if (alarmid != -1) {
                viewModelScope.launch {
                    alarmUseCases.getAlarm(alarmid)?.also { alarm ->
                        currentAlarmId = alarm.id
                        _alarmTitle.value = _alarmTitle.value.copy(
                            text = alarm.title,
                            isHintIsVisible = false
                        )
                        _containerColor.value = alarm.color
                        _pickerState.value = _pickerState.value.copy(
                            hour = alarm.hour.toString(),
                            minute = alarm.minute.toString(),
                            period = alarm.period.toString()

                        )

                        _weekDays.value = _weekDays.value.copy(weekDays = alarm.weekDays)


                         _ringtoneTitle.value = alarm.ringtoneTitle
                         _ringtoneUri.value = alarm.ringtoneUri
                         _isVibrationEnabled.value = alarm.isVibrationEnabled
                         _snoozeDuration.value = alarm.snoozeDuration
                         _snoozeCount.value = alarm.snoozeCount
                    }
                }
            }
        }
    }

    private fun loadAlarms() {
        viewModelScope.launch {
            alarmUseCases.getAlarms().collect { alarms ->
                Log.d("AddEditAlarm", "Alarms loaded: ${alarms.size}")
                _state.value = _state.value.copy(alarm = alarms)
            }
        }
    }

    fun onEvent(event: AddEditAlarmEvent) {
        when (event) {
            is AddEditAlarmEvent.EnteredTitle -> {
                _alarmTitle.value = _alarmTitle.value.copy(
                    text = event.value
                )
            }
            is AddEditAlarmEvent.ChangeTitleFocus -> {
                _alarmTitle.value = _alarmTitle.value.copy(
                    isHintIsVisible = !event.focus.isFocused &&
                            _alarmTitle.value.text.isBlank()
                )
            }
            is AddEditAlarmEvent.SaveAlarm -> {
                Log.d("AddEditAlarm", "Save Alarm Event recived in viewmodel")
                viewModelScope.launch {
                    try {
                        alarmUseCases.addAlarm(
                            Alarm(
                                title = _alarmTitle.value.text,
                                color = containerColor.value,
                                id = currentAlarmId,
                                hour = _pickerState.value.hour,
                                minute = _pickerState.value.minute,
                                period = _pickerState.value.period,
                                weekDays = _weekDays.value.weekDays,
                                date = _selectedDays.value,
                                ringtoneTitle = _ringtoneTitle.value,
                                ringtoneUri = _ringtoneUri.value,
                                isVibrationEnabled = _isVibrationEnabled.value,
                                snoozeDuration = _snoozeDuration.value,
                                snoozeCount = _snoozeCount.value
                            )
                        )
                        _eventFlow.emit(uiEvent.saveAlarm)
                        Log.d("AddEditAlarm", "Alarm saved successfully")
                    } catch (e: InvalidAlarmException) {
                        _eventFlow.emit(
                            uiEvent.ShowSnackBar(
                                message = e.message ?: "Unkowon Erorr"
                            )
                        )
                        Log.e("AddEditAlarm", "Error saving alarm: ${e.message}", e)
                    }
                }
            }
            is AddEditAlarmEvent.CancelAlarm -> {
            }

            is AddEditAlarmEvent.SelectedHour -> {
                _pickerState.value = _pickerState.value.copy(hour= event.hour)
            }

            is AddEditAlarmEvent.SelectedMinute -> {
                _pickerState.value = _pickerState.value.copy(minute = event.minute)
            }

            is AddEditAlarmEvent.SelectedPeriod -> {
                _pickerState.value = _pickerState.value.copy(period = event.period)
            }

            is AddEditAlarmEvent.SelectedWeekDays -> {
                _weekDays.value = _weekDays.value.copy(weekDays = event.weekDays)
            }

            is AddEditAlarmEvent.SelectedChallengeType -> {
                _challengeType.value = event.type
            }

            // ✅ নতুন যোগ করা হলো
            is AddEditAlarmEvent.SelectedRingtone -> {
                _ringtoneTitle.value = event.title
                _ringtoneUri.value = event.uriString
            }

//            is AddEditAlarmEvent.ToggleVibration -> {
//                _isVibrationEnabled.value = !_isVibrationEnabled.value
//            }

            is AddEditAlarmEvent.SelectedVibrationPattern -> {
                _vibrationPattern.value = event.pattern
            }

            is AddEditAlarmEvent.SelectedSnooze -> {
                _snoozeDuration.value = event.duration
                _snoozeCount.value = event.count
            }

            else -> {}
        }
    }

    sealed class uiEvent {
        data class ShowSnackBar(val message: String) : uiEvent()
        object saveAlarm : uiEvent()
    }

    companion object {
        val hours = (1..12).map{it.toString()}
        val minutes: List<String> = (0..59).map {it.toString().padStart(2, '0')}
        val period = listOf("am", "pm")
        val weekDays = listOf( "M", "T", "W", "T", "F", "S", "S")
    }
}