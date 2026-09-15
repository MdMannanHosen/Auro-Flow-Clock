package com.mannanhosen.auraflowclock.presentation.add_edit_alarm.add_alarm_viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mannanhosen.auraflowclock.data.model.ChallengeType
import com.mannanhosen.auraflowclock.domain.usecase.alarm.SetAlarmChallengeUseCase
import com.mannanhosen.auraflowclock.presentation.add_edit_alarm.add_edit_ui_state.ChallengeMethodUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChallengeMethodViewModel @Inject constructor(
    private val setAlarmChallengeUseCase: SetAlarmChallengeUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val alarmId: Int = savedStateHandle.get<Int>("alarmId") ?: -1
    private val _uiState = MutableStateFlow(ChallengeMethodUiState(alarmId = alarmId))
    val uiState: StateFlow<ChallengeMethodUiState> = _uiState.asStateFlow()

    fun onChallengeSelected(type: ChallengeType) {
        _uiState.update { it.copy(selectedType = type) }
    }

    fun saveChallenge(qrValue: String? = null) {
        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true) }
            setAlarmChallengeUseCase(alarmId, _uiState.value.selectedType, qrValue)
            _uiState.update { it.copy(isSaving = false, isSaved = true) }
        }
    }
}