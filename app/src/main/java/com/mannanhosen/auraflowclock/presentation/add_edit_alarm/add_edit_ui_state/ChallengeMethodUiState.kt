package com.mannanhosen.auraflowclock.presentation.add_edit_alarm.add_edit_ui_state

import com.mannanhosen.auraflowclock.data.model.ChallengeType

data class ChallengeMethodUiState(
    val alarmId: Int = -1,
    val selectedType: ChallengeType = ChallengeType.DEFAULT,
    val isSaving: Boolean = false,
    val isSaved: Boolean = false
)