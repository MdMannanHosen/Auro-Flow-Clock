package com.mannanhosen.auraflowclock.presentation.alarm.components
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Snooze
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mannanhosen.auraflowclock.data.model.entity.Alarm

@Composable
fun alarmItem(

    alarm: Alarm,
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit = {},
    onToggleEnabled: (Boolean) -> Unit = {},
    onChallengeClick: (Int) -> Unit = {},
    onItemClick: () -> Unit = {}
) {
    var isEnabled by remember(key1 = alarm.id) { mutableStateOf(alarm.isEnabled) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2B2D2F)),
        onClick = onItemClick
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // ---- টপ রো: টাইম + টগল ----
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "${alarm.hour}:${alarm.minute}",
                        fontSize = 40.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (isEnabled) Color.White else Color(0xFF6B6E70)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = alarm.period,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = if (isEnabled) Color(0xFFB0B3B5) else Color(0xFF5A5C5E),
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                }

                Switch(
                    checked = isEnabled,
                    onCheckedChange = {
                        isEnabled = it
                        onToggleEnabled(it)
                    },
                    colors = SwitchDefaults.colors(
                        checkedTrackColor = Color(0xFF4CAF50),
                        uncheckedTrackColor = Color(0xFF3A3C3E)
                    )
                )
            }

            // ---- টাইটেল ----
            if (alarm.title.isNotBlank()) {
                Text(
                    text = alarm.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    color = if (isEnabled) Color.White else Color(0xFF6B6E70),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            // ---- সপ্তাহের দিন ----
            Text(
                text = alarm.weekDays.ifBlank { "One time" },
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF9E9E9E),
                maxLines = 1,
                modifier = Modifier.padding(top = 2.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = Color(0xFF3A3C3E), thickness = 1.dp)
            Spacer(modifier = Modifier.height(10.dp))

            // ---- বটম রো: চ্যালেঞ্জ / রিংটোন / ভাইব্রেশন / স্নুজ / ডিলিট ----
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {

                    InfoChip(
                        icon = Icons.Default.EmojiEvents,
                        label = alarm.challengeType.name,
                        tint = Color(0xFFFFC107),
                        onClick = { alarm.id?.let { onChallengeClick(it) } }
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    InfoChip(
                        icon = Icons.Default.MusicNote,
                        label = alarm.ringtoneName.ifBlank { "Default" },
                        tint = Color(0xFF64B5F6)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    InfoChip(
                        icon = Icons.Default.Vibration,
                        label = if (alarm.isVibrationEnabled) "On" else "Off",
                        tint = Color(0xFFFF9800)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    InfoChip(
                        icon = Icons.Default.Snooze,
                        label = "${alarm.snoozeDuration}m",
                        tint = Color(0xFF03A9F4)
                    )
                }

                IconButton(onClick = onDeleteClick, modifier = Modifier.size(32.dp)) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete alarm",
                        tint = Color(0xFFEF5350)
                    )
                }
            }
        }
    }
}

@Composable
private fun InfoChip(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    tint: Color,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFF202224))
            .let { if (onClick != null) it.clickable(onClick = onClick) else it }
            .padding(horizontal = 8.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = label, tint = tint, modifier = Modifier.size(14.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = label,
            fontSize = 11.sp,
            color = Color(0xFFCFCFCF),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}