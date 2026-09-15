package com.mannanhosen.auraflowclock.presentation.add_edit_alarm.screen
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Snooze
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mannanhosen.auraflowclock.data.model.ChallengeType
import com.mannanhosen.auraflowclock.presentation.add_edit_alarm.componentes.AddEditAlarmEvent
import com.mannanhosen.auraflowclock.presentation.add_edit_alarm.add_alarm_viewmodel.AddEditAlarmViewModel
import com.mannanhosen.auraflowclock.presentation.add_edit_alarm.add_alarm_viewmodel.AddEditAlarmViewModel.Companion.hours
import com.mannanhosen.auraflowclock.presentation.add_edit_alarm.add_alarm_viewmodel.AddEditAlarmViewModel.Companion.minutes
import com.mannanhosen.auraflowclock.presentation.add_edit_alarm.add_alarm_viewmodel.AddEditAlarmViewModel.Companion.period
import com.mannanhosen.auraflowclock.presentation.alarm.components.AlarmTimePicker
import com.mannanhosen.auraflowclock.presentation.alarm.components.ChallengeSelectorCard
import com.mannanhosen.auraflowclock.presentation.alarm.components.EveryDay
import com.mannanhosen.auraflowclock.presentation.alarm.components.SettingSelectorCard
import com.mannanhosen.auraflowclock.presentation.alarm.components.TransparentHintTextField

@SuppressLint("RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditAlarmScreen(
    onCancel: () -> Unit = {},
    onSave: () -> Unit = {},
    navController: NavController? = null,
    viewModel: AddEditAlarmViewModel = hiltViewModel()
) {
    val titleState = viewModel.alarmTitle.value
    val snackbarHostState = remember { SnackbarHostState() }
    val hoursList = hours
    val minuteList = minutes
    val periodList = period
    val weekDaysState by viewModel.weekDays

    val challengeTypeState by viewModel.challengeType

    val startIndex = 1000
    val hourState = rememberPagerState(
        pageCount = { Int.MAX_VALUE },
        initialPage = startIndex * hoursList.size + 0
    )

    val minuteState = rememberPagerState(
        pageCount = { Int.MAX_VALUE },
        initialPage = startIndex * minuteList.size + 0
    )

    val periodState = rememberPagerState(
        pageCount = { periodList.size }
    )

    LaunchedEffect(hourState) {
        snapshotFlow { hourState.currentPage }
            .collect { page ->
                val actualIndex = page % hoursList.size
                viewModel.onEvent(AddEditAlarmEvent.SelectedHour(hoursList[actualIndex]))
            }
    }

    LaunchedEffect(minuteState) {
        snapshotFlow { minuteState.currentPage }
            .collect { page ->
                val actualIndex = page % minuteList.size
                viewModel.onEvent(AddEditAlarmEvent.SelectedMinute(minuteList[actualIndex]))
            }
    }

    LaunchedEffect(periodState) {
        snapshotFlow { periodState.currentPage }
            .collect { page ->
                viewModel.onEvent(AddEditAlarmEvent.SelectedPeriod(periodList[page]))
            }
    }

    // ✅ নতুন যোগ করা হলো — Challenge Method Screen থেকে ফিরে আসার সময়
    //    savedStateHandle থেকে ফলাফল সংগ্রহ করে ViewModel কে জানানো হচ্ছে
    LaunchedEffect(key1 = true) {
        navController
            ?.currentBackStackEntry
            ?.savedStateHandle
            ?.getStateFlow<String?>("selected_challenge_type", null)
            ?.collect { result ->
                result?.let { typeName ->
                    viewModel.onEvent(
                        AddEditAlarmEvent.SelectedChallengeType(ChallengeType.valueOf(typeName))
                    )
                    // ব্যবহার হয়ে গেলে clear করে দেওয়া, নাহলে বার বার trigger হবে
                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.set("selected_challenge_type", null)
                }
            }
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is AddEditAlarmViewModel.uiEvent.saveAlarm -> {
                    Log.d("AddEditAlarm", "saveAlarm event collected in UI, navigating back")
                    onSave()   // এখানে আসল নেভিগেশন/close কল হবে
                }
                is AddEditAlarmViewModel.uiEvent.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(message = event.message)
                }
            }
        }
    }


    LaunchedEffect(key1 = true) {
        navController
            ?.currentBackStackEntry
            ?.savedStateHandle
            ?.getStateFlow<String?>("selected_ringtone_title", null)
            ?.collect { title ->
                title?.let {
                    val uri = navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.get<String?>("selected_ringtone_uri")

                    viewModel.onEvent(AddEditAlarmEvent.SelectedRingtone(it, uri ?: ""))

                    navController.currentBackStackEntry?.savedStateHandle?.set("selected_ringtone_title", null)
                    navController.currentBackStackEntry?.savedStateHandle?.set("selected_ringtone_uri", null)
                }
            }
    }

    LaunchedEffect(key1 = true) {
        navController
            ?.currentBackStackEntry
            ?.savedStateHandle
            ?.getStateFlow<String?>("selected_vibration_pattern", null)
            ?.collect { pattern ->
                pattern?.let {
                    viewModel.onEvent(AddEditAlarmEvent.SelectedVibrationPattern(it))
                    navController.currentBackStackEntry?.savedStateHandle?.set("selected_vibration_pattern", null)
                }
            }
    }

    LaunchedEffect(key1 = true) {
        navController
            ?.currentBackStackEntry
            ?.savedStateHandle
            ?.getStateFlow<Int?>("selected_snooze_duration", null)
            ?.collect { duration ->
                duration?.let {
                    val count = navController.currentBackStackEntry
                        ?.savedStateHandle?.get<Int?>("selected_snooze_count") ?: 3
                    viewModel.onEvent(AddEditAlarmEvent.SelectedSnooze(it, count))
                    navController.currentBackStackEntry?.savedStateHandle?.set("selected_snooze_duration", null)
                    navController.currentBackStackEntry?.savedStateHandle?.set("selected_snooze_count", null)
                }
            }
    }

    AddEditAlarmContent(
        titleText = titleState.text,
        titleHint = titleState.hint,
        isHintVisible = titleState.isHintIsVisible,
        onTitleChange = { viewModel.onEvent(AddEditAlarmEvent.EnteredTitle(it)) },
        onTitleFocusChange = {
            viewModel.onEvent(AddEditAlarmEvent.ChangeTitleFocus(it))
        },
        onCancel = onCancel,
        onSave = {
            Log.d("AddEditAlarm", "sending SaveAlarm Event")
            viewModel.onEvent(AddEditAlarmEvent.SaveAlarm)
        },
        hoursList = hoursList,
        minuteList = minuteList,
        periodList = periodList,
        hourState = hourState,
        minuteState = minuteState,
        periodState = periodState,
        selectedWeekDays = weekDaysState.weekDays,
        onWeekDaysChanged = { days -> viewModel.onEvent(AddEditAlarmEvent.SelectedWeekDays(days)) },
        // ✅ নতুন যোগ করা হলো
        selectedChallengeType = challengeTypeState,
        onChallengeClick = { navController?.navigate("challenge_method_screen") },
        snackbarHostState = snackbarHostState,
        onRingtoneClick = { navController?.navigate("ringtone_picker_screen") },
        onSnoozeClick = {
            navController?.navigate("snooze_screen?duration=${viewModel.snoozeDuration.value}&count=${viewModel.snoozeCount.value}")
        },
        onVibrationClick = {
            navController?.navigate("vibration_screen?currentPattern=${viewModel.vibrationPattern.value}")
        }

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditAlarmContent(
    titleText: String = "",
    titleHint: String = "Alarm name",
    isHintVisible: Boolean = true,
    onTitleChange: (String) -> Unit = {},
    onTitleFocusChange: (FocusState) -> Unit = {},
    onCancel: () -> Unit = {},
    onSave: () -> Unit = {},
    hoursList: List<String> = hours,
    minuteList: List<String> = minutes,
    periodList: List<String> = period,
    hourState: PagerState = rememberPagerState(pageCount = { hoursList.size }),
    minuteState: PagerState = rememberPagerState(pageCount = { minuteList.size }),
    periodState: PagerState = rememberPagerState(pageCount = { periodList.size }),
    selectedWeekDays: String = "",
    onWeekDaysChanged: (String) -> Unit = {},
    selectedChallengeType: ChallengeType = ChallengeType.DEFAULT,
    onChallengeClick: () -> Unit = {},
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    selectedRingtoneName: String = "Default",
    isVibrationEnabled: Boolean = true,
    snoozeDuration: Int = 5,
    snoozeCount: Int = 3,
    onRingtoneClick: () -> Unit = {},
    onVibrationClick: () -> Unit = {},
    onSnoozeClick: () -> Unit = {},
) {
    Scaffold(

        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Add alarm", color = Color.White, fontWeight = FontWeight.Medium, fontSize = 18.sp)
                        Text("Alarm in 23 hours 58 minutes", color = Color(0xFF9E9E9E), fontSize = 12.sp)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onCancel, modifier = Modifier.size(48.dp)) {
                        Icon(Icons.Default.Cancel, contentDescription = "Cancel", tint = Color.White, modifier = Modifier.size(24.dp))
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            Log.d("AddEditAlarm", "Save Button Click")
                            onSave()
                        },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(Icons.Default.Check, contentDescription = "Save", tint = Color.White, modifier = Modifier.size(24.dp))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF202224),
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFF202224))
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            AlarmTimePicker(
                hours = hoursList,
                minutes = minuteList,
                periods = periodList,
                hourState = hourState,
                minutesState = minuteState,
                periodState = periodState
            )

            TransparentHintTextField(
                text = titleText,
                hint = titleHint,
                onValueChange = onTitleChange,
                onFocusChange = onTitleFocusChange,
                isHintVisible = isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.titleLarge,
            )

            EveryDay(
                selectedWeekDays = selectedWeekDays,
                onWeekDaysChanged = onWeekDaysChanged,
                onCalendarClick = {
                    // TODO: calendar dialog দেখানো
                }
            )

            ChallengeSelectorCard(
                selectedChallenge = selectedChallengeType,
                onClick = onChallengeClick,
                modifier = Modifier.padding(top = 12.dp),
                )

            Spacer(modifier = Modifier.height(8.dp))

            SettingSelectorCard(
                icon = Icons.Default.MusicNote,
                title = "Ringtone",
                subtitle = selectedRingtoneName,
                onClick = onRingtoneClick
            )

            Spacer(modifier = Modifier.height(8.dp))

            SettingSelectorCard(
                icon = Icons.Default.Vibration,
                iconTint = Color(0xFFFF9800),
                title = "Vibration",
                subtitle = if (isVibrationEnabled) "On" else "Off",
                onClick = onVibrationClick
            )

            Spacer(modifier = Modifier.height(8.dp))

            SettingSelectorCard(
                icon = Icons.Default.Snooze,
                iconTint = Color(0xFF03A9F4),
                title = "Snooze",
                subtitle = "$snoozeDuration min, $snoozeCount times",
                onClick = onSnoozeClick
            )
        }
    }
}


@Preview(showBackground = true, device = Devices.PIXEL_4_XL)
@Composable
fun PreviewAlarmScreen() {
    AddEditAlarmContent()
}