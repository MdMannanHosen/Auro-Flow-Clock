package com.mannanhosen.auraflowclock.presentation.add_edit_alarm.componentes
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SnoozeSettingDialog(
    currentDuration: Int,
    currentCount: Int,
    onDismiss: () -> Unit,
    onConfirm: (duration: Int, count: Int) -> Unit
) {
    var duration by remember { mutableStateOf(currentDuration) }
    var count by remember { mutableStateOf(currentCount) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Snooze Settings") },
        text = {
            Column {
                Text("Snooze duration: $duration min")
                Slider(
                    value = duration.toFloat(),
                    onValueChange = { duration = it.toInt() },
                    valueRange = 1f..30f,
                    steps = 28
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text("Snooze count: $count times")
                Slider(
                    value = count.toFloat(),
                    onValueChange = { count = it.toInt() },
                    valueRange = 1f..10f,
                    steps = 8
                )
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(duration, count) }) { Text("Save") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}