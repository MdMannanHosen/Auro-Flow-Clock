package com.mannanhosen.auraflowclock.presentation.alarm.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.mannanhosen.auraflowclock.presentation.add_edit_alarm.add_alarm_viewmodel.AddEditAlarmViewModel

@Composable
fun AlarmScreen(
    navController: NavController,
    viewModel: AddEditAlarmViewModel = hiltViewModel()
) {

    val state = viewModel.state.value
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
        items (state.alarm, key = { it.id ?: 0}) { alarm ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp) // আপনার alarmItem-এর হাইট অনুযায়ী এটি এডজাস্ট করতে পারেন
            ) {

                alarmItem(
                    alarm = alarm,
                    modifier = Modifier
                        .fillMaxSize(),
                    onDeleteClick = {},
                    onChallengeClick = { alarmId ->
                        navController.navigate("challenge_method_screen/$alarmId")
                    }

                )
            }
        }
        }
    }
}