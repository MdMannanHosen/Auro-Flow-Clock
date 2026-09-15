package com.mannanhosen.auraflowclock.presentation.add_edit_alarm.componentes
import android.Manifest
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

data class VibrationPattern(val title: String, val pattern: LongArray)

private val vibrationPatterns = listOf(
    VibrationPattern("Off", longArrayOf(0)),
    VibrationPattern("Gentle", longArrayOf(0, 80, 400)),
    VibrationPattern("Short Pulse", longArrayOf(0, 200, 200)),
    VibrationPattern("Long Pulse", longArrayOf(0, 500, 300)),
    VibrationPattern("Rapid Pulse", longArrayOf(0, 100, 100, 100, 100, 100, 100)),
    VibrationPattern("Double Pulse", longArrayOf(0, 200, 150, 200, 400)),
    VibrationPattern("Heartbeat", longArrayOf(0, 100, 100, 100, 300)),
    VibrationPattern("Escalating", longArrayOf(0, 100, 300, 200, 200, 300, 100, 400, 50)),
    VibrationPattern("Wave", longArrayOf(0, 100, 50, 200, 50, 300, 50, 200, 50, 100)),
    VibrationPattern("SOS", longArrayOf(0, 150, 100, 150, 100, 150, 300, 400, 100, 400, 100, 400, 300, 150, 100, 150, 100, 150)),
    VibrationPattern("Continuous", longArrayOf(0, 1000))
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VibrationScreen(
    navController: NavController,
    currentPattern: String = "Short Pulse"
) {
    val context = LocalContext.current
    var selectedPattern by remember { mutableStateOf(currentPattern) }

    @RequiresPermission(Manifest.permission.VIBRATE)
    fun vibrate(pattern: LongArray) {
        // ✅ Off সিলেক্ট করলে preview vibrate করার দরকার নেই
        if (pattern.size == 1 && pattern[0] == 0L) return

        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val manager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            manager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createWaveform(pattern, -1))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(pattern, -1)
        }
    }

    Scaffold(
        containerColor = Color(0xFF202224),
        topBar = {
            TopAppBar(
                title = { Text("Vibration", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                actions = {
                    TextButton(onClick = {
                        navController.previousBackStackEntry
                            ?.savedStateHandle
                            ?.set("selected_vibration_pattern", selectedPattern)
                        navController.popBackStack()
                    }) {
                        Text("Save", color = Color(0xFF00E5A0))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF202224))
            )
        }
    ) { padding ->
        // ✅ LazyColumn ব্যবহার করা হয়েছে যাতে ১০+ item স্মুথভাবে scroll করা যায়
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            items(vibrationPatterns) { item ->
                val isSelected = item.title == selectedPattern

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isSelected) Color(0xFF2A2C2E) else Color.Transparent)
                        .clickable {
                            selectedPattern = item.title
                            vibrate(item.pattern) // ✅ সিলেক্ট করলেই preview vibrate হবে
                        }
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        item.title,
                        color = Color.White,
                        fontWeight = FontWeight.Medium,
                        fontSize = 15.sp,
                        modifier = Modifier.weight(1f)
                    )
                    if (isSelected) {
                        Icon(Icons.Default.Check, contentDescription = "Selected", tint = Color(0xFF00E5A0))
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}