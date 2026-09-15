@file:Suppress("PreviewAnnotationInFunctionWithParameters")
package com.mannanhosen.auraflowclock.presentation.add_edit_alarm.componentes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mannanhosen.auraflowclock.presentation.Navigation.AppRoutes
import com.mannanhosen.auraflowclock.presentation.alarm.components.SettingsSwitch


@Preview(showBackground = true, device = Devices.PIXEL_4_XL)
@Composable
fun AlarmSettingsCard(
    alarmName: String,
    onAlarmNameChange: (String) -> Unit,
    alarmSoundEnabled: Boolean,
    onAlarmSoundChange: (Boolean) -> Unit,
    vibrationEnabled: Boolean,
    onVibrationChange: (Boolean) -> Unit,
    snoozeEnabled: Boolean,
    onSnoozeChange: (Boolean) -> Unit,
    onCalendarClick: () -> Unit,
    navController: NavController?,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2A2C30))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onCalendarClick() }
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // TODO: এখানে নির্বাচিত তারিখ/রিপিট টেক্সট দেখাতে পারেন
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Alarm Name", color = Color.Gray) },
            value = alarmName,
            onValueChange = onAlarmNameChange,
            textStyle = TextStyle(color = Color.White, fontSize = 16.sp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFE91E63),
                unfocusedBorderColor = Color(0xFF3A3C40),
                focusedLabelColor = Color(0xFFE91E63),
                cursorColor = Color(0xFFE91E63)
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        SettingsSwitch(
            title = "Alarm Sound",
            checked = alarmSoundEnabled,
            onCheckedChange = onAlarmSoundChange,
            onClick = { safeNavigate(navController, AppRoutes.AlarmSound.routes) }
        )

        Spacer(modifier = Modifier.height(8.dp))

        SettingsSwitch(
            title = "Vibration",
            checked = vibrationEnabled,
            onCheckedChange = onVibrationChange,
            onClick = { safeNavigate(navController, AppRoutes.Vibration.routes) }
        )

        Spacer(modifier = Modifier.height(8.dp))

        SettingsSwitch(
            title = "Snooze",
            checked = snoozeEnabled,
            onCheckedChange = onSnoozeChange,
            onClick = { safeNavigate(navController, AppRoutes.Vibration.routes) }
        )
    }
}

/**
 * ছোট helper - বারবার একই try/catch navigate কোড না লিখে
 * এখান থেকে কল করা যাবে।
 */
private fun safeNavigate(navController: NavController?, route: String) {
    if (navController != null && navController.currentDestination != null) {
        try {
            navController.navigate(route)
        } catch (e: Exception) {
            android.util.Log.e("NavigationError", "Failed to navigate: ${e.message}")
        }
    }
}
