package com.mannanhosen.auraflowclock.presentation.add_edit_alarm.screen


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SnoozeScreen(
    navController: NavController,
    currentDuration: Int = 5,
    currentCount: Int = 3
) {
    var duration by remember { mutableStateOf(currentDuration) }
    var count by remember { mutableStateOf(currentCount) }

    Scaffold(
        containerColor = Color(0xFF202224),
        topBar = {
            TopAppBar(
                title = { Text("Snooze", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                actions = {
                    TextButton(onClick = {
                        navController.previousBackStackEntry
                            ?.savedStateHandle
                            ?.set("selected_snooze_duration", duration)
                        navController.previousBackStackEntry
                            ?.savedStateHandle
                            ?.set("selected_snooze_count", count)
                        navController.popBackStack()
                    }) {
                        Text("Save", color = Color(0xFF00E5A0))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF202224))
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF202224))
                .padding(padding)
                .padding(24.dp)
        ) {
            Text("Snooze duration", color = Color.White, fontWeight = FontWeight.Medium, fontSize = 15.sp)
            Text("$duration minutes", color = Color(0xFF00E5A0), fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Slider(
                value = duration.toFloat(),
                onValueChange = { duration = it.toInt() },
                valueRange = 1f..30f,
                steps = 28,
                colors = SliderDefaults.colors(
                    thumbColor = Color(0xFF00E5A0),
                    activeTrackColor = Color(0xFF00E5A0)
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text("Snooze count", color = Color.White, fontWeight = FontWeight.Medium, fontSize = 15.sp)
            Text(
                if (count == 0) "Unlimited" else "$count times",
                color = Color(0xFF00E5A0),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Slider(
                value = count.toFloat(),
                onValueChange = { count = it.toInt() },
                valueRange = 0f..10f,
                steps = 9,
                colors = SliderDefaults.colors(
                    thumbColor = Color(0xFF00E5A0),
                    activeTrackColor = Color(0xFF00E5A0)
                )
            )
        }
    }
}