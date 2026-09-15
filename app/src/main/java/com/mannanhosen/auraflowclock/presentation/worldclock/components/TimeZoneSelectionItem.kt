package com.mannanhosen.auraflowclock.presentation.worldclock.components
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mannanhosen.auraflowclock.data.model.entity.TimeZone

// ---- App-wide palette anchors (kept in sync with TimeZoneCard.kt) ----
private val AppAccent = Color(0xFF00E5A0)
private val CardSurfaceUnselected = Color(0xFF2A2D30)
private val CardSurfaceSelected = Color(0xFF223330) // subtle mint-tinted dark surface
private val CardBorderSelected = AppAccent.copy(alpha = 0.4f)
private val CardBorderUnselected = Color.White.copy(alpha = 0.06f)
private val TextPrimary = Color(0xFFF5F7F6)
private val TextSecondary = Color(0xFF8A8F94)

@Composable
fun TimeZoneSelectionItem(
    timeZone: TimeZone,
    onSelectinChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val containerColor by animateColorAsState(
        targetValue = if (timeZone.isSelected) CardSurfaceSelected else CardSurfaceUnselected,
        animationSpec = tween(250),
        label = "containerColor"
    )
    val borderColor by animateColorAsState(
        targetValue = if (timeZone.isSelected) CardBorderSelected else CardBorderUnselected,
        animationSpec = tween(250),
        label = "borderColor"
    )
    val titleColor by animateColorAsState(
        targetValue = if (timeZone.isSelected) AppAccent else TextPrimary,
        animationSpec = tween(250),
        label = "titleColor"
    )

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (timeZone.isSelected) 4.dp else 1.dp
        ),
        border = androidx.compose.foundation.BorderStroke(
            width = if (timeZone.isSelected) 1.5.dp else 1.dp,
            color = borderColor
        ),
        onClick = { onSelectinChange(!timeZone.isSelected) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = timeZone.displayName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    color = titleColor
                )

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = timeZone.timeZoneName,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (timeZone.isSelected) {
                        AppAccent.copy(alpha = 0.7f)
                    } else {
                        TextSecondary
                    }
                )
            }

            Checkbox(
                checked = timeZone.isSelected,
                onCheckedChange = onSelectinChange,
                colors = CheckboxDefaults.colors(
                    checkedColor = AppAccent,
                    uncheckedColor = TextSecondary,
                    checkmarkColor = Color(0xFF17181A)
                )
            )
        }
    }
}