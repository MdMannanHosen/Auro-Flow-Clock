package com.mannanhosen.auraflowclock.presentation.alarm.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import java.util.Date

/**
 * আগে এটা AddEditAlarmContent এর ভেতরে nested composable ছিল
 * (তাই @Preview কাজ করছিল না)। এখন top-level, আলাদা ফাইলে।
 */
@Composable
fun RealCalenderDialog(
    onDateSelected: (Date) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF2A2C30))
        ) {
            RealCalendarView(
                onDateSelected = onDateSelected,
                onDismiss = onDismiss
            )
        }
    }
}
