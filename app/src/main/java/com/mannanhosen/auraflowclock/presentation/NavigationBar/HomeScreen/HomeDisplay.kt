package com.mannanhosen.auraflowclock.presentation.NavigationBar.HomeScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.mannanhosen.auraflowclock.presentation.add_edit_alarm.add_alarm_viewmodel.AddEditAlarmViewModel
import com.mannanhosen.auraflowclock.presentation.alarm.components.alarmItem

@Composable
fun HomeDisplay(
    modifier: Modifier = Modifier,
    navController: NavController? = null,   // ✅ নতুন যোগ করা হলো — navigation এর জন্য
    viewModel: AddEditAlarmViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(state.alarm, key = { it.id ?: 0 }) { alarm ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(110.dp)
                ) {
                    alarmItem(
                        alarm = alarm,
                        modifier = Modifier.fillMaxSize(),
                        onDeleteClick = {},
                        onChallengeClick = { alarmId ->
                            navController?.navigate("challenge_method_screen?alarmId=$alarmId")
                        }
                    )
                }
            }
        }
    }
}

// ✅ Preview কে আলাদা রাখা হলো, কারণ hiltViewModel() আর NavController
//    আসল App এ Hilt/NavHost এর ভিতরেই কাজ করে — Preview তে এগুলো inject হয় না।
//    তাই Preview এর জন্য navController = null রেখে দিলাম, alarmItem এর ভেতরে
//    সেটা safe-call (?.navigate) দিয়ে হ্যান্ডেল হচ্ছে বলে crash করবে না।
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeDisplayPreview() {
    HomeDisplay()
}