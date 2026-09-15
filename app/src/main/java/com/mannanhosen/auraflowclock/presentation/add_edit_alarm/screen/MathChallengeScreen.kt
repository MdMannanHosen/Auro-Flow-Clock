package com.mannanhosen.auraflowclock.presentation.add_edit_alarm.screen

import com.mannanhosen.auraflowclock.presentation.challenge.dismiss.MathChallengeViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun MathChallengeScreen(
    onSolved: () -> Unit,
    viewModel: MathChallengeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var userInput by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.isSolved) {
        if (uiState.isSolved) onSolved()
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
            "Solve to dismiss alarm",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            "${uiState.num1} ${uiState.operator} ${uiState.num2} = ?",
            color = Color.White,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = userInput,
            onValueChange = {
                userInput = it
                showError = false
            },
            label = { Text("Your Answer") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = showError,
            modifier = Modifier.fillMaxWidth()
        )

        if (showError) {
            Text(
                "Wrong answer, try again!",
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val answer = userInput.toIntOrNull()
                if (answer != null) {
                    val correct = viewModel.checkAnswer(answer)
                    if (!correct) {
                        showError = true
                        userInput = ""
                        viewModel.regenerateProblem() // নতুন problem দেওয়া হচ্ছে ভুল হলে
                    }
                } else {
                    showError = true
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit")
        }
    }
}
