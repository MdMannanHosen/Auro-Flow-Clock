package com.mannanhosen.auraflowclock.presentation.add_edit_alarm.screen

import com.mannanhosen.auraflowclock.presentation.add_edit_alarm.add_alarm_viewmodel.ShakeChallengeViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ShakeChallengeScreen(
    onSolved: () -> Unit,
    viewModel: ShakeChallengeViewModel = hiltViewModel()
) {
    val shakeCount by viewModel.shakeCount.collectAsState()
    val requiredShakes = 15

    var hasSolved by remember { mutableStateOf(false) } // ✅ একবারই কল হওয়া নিশ্চিত করার জন্য

    DisposableEffect(Unit) {
        viewModel.startListening()
        onDispose { viewModel.stopListening() }
    }

    LaunchedEffect(shakeCount) {
        if (!hasSolved && viewModel.isChallengeComplete()) {
            hasSolved = true      // ✅ flag সেট করে দিচ্ছি
            viewModel.stopListening() // ✅ sensor বন্ধ, আর কাউন্ট বাড়বে না
            onSolved()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF202224))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Shake your phone to dismiss alarm!",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(32.dp))

        LinearProgressIndicator(
            progress = (shakeCount.toFloat() / requiredShakes).coerceIn(0f, 1f),
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp),
            color = Color(0xFF00E5A0)
        )
        Spacer(modifier = Modifier.height(12.dp))

        Text(
            "$shakeCount / $requiredShakes",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}