package com.mannanhosen.auraflowclock.presentation.add_edit_alarm.componentes

//import androidx.compose.ui.focus.FocusState
//import com.mannanhosen.auraflowclock.data.model.ChallengeType
//
//sealed class AddEditAlarmEvent {
//    data class EnteredTitle(val value : String) : AddEditAlarmEvent()
//    data class ChangeTitleFocus (val focus: FocusState) : AddEditAlarmEvent()
//    data class ChangeColor(val color : Int) : AddEditAlarmEvent()
//    data class SelectedHour(val hour : String) : AddEditAlarmEvent()
//    data class SelectedMinute(val minute: String) : AddEditAlarmEvent()
//    data class SelectedPeriod(val period : String) : AddEditAlarmEvent()
//    data class SelectedChallengeType(val type: ChallengeType) : AddEditAlarmEvent()
//    data class SelectedWeekDays(val weekDays: String) : AddEditAlarmEvent()
//    object SaveAlarm : AddEditAlarmEvent()
//    object DeleteAlarm : AddEditAlarmEvent()
//    object CancelAlarm : AddEditAlarmEvent()
//
//}


import androidx.compose.ui.focus.FocusState
import com.mannanhosen.auraflowclock.data.model.ChallengeType

sealed class AddEditAlarmEvent {
    data class EnteredTitle(val value : String) : AddEditAlarmEvent()
    data class ChangeTitleFocus (val focus: FocusState) : AddEditAlarmEvent()
    data class ChangeColor(val color : Int) : AddEditAlarmEvent()
    data class SelectedHour(val hour : String) : AddEditAlarmEvent()
    data class SelectedMinute(val minute: String) : AddEditAlarmEvent()
    data class SelectedPeriod(val period : String) : AddEditAlarmEvent()
    data class SelectedChallengeType(val type: ChallengeType) : AddEditAlarmEvent()
    data class SelectedWeekDays(val weekDays: String) : AddEditAlarmEvent()

    // ✅ নতুন যোগ করা হলো
    data class SelectedRingtone(val title: String, val uriString: String) : AddEditAlarmEvent()
    data class SelectedSnooze(val duration: Int, val count: Int) : AddEditAlarmEvent()
    data class SelectedVibrationPattern(val pattern: String) : AddEditAlarmEvent() // ✅ ToggleVibration-এর বদলে

    object SaveAlarm : AddEditAlarmEvent()
    object DeleteAlarm : AddEditAlarmEvent()
    object CancelAlarm : AddEditAlarmEvent()
}


