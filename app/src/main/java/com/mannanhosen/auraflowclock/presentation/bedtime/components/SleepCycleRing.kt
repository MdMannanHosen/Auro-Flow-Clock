package com.mannanhosen.auraflowclock.presentation.bedtime.components
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.mannanhosen.auraflowclock.ui.theme.AccentMint
import com.mannanhosen.auraflowclock.ui.theme.TextPrimary
import com.mannanhosen.auraflowclock.ui.theme.TextSecondary



@Composable
fun SleepCycleRing(
    bedtimeText: String,
    cyclesText: String,
    durationText: String,
    totalSegments: Int = 24,
    filledSegments: Int = 20,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth(0.6f) // was fillMaxWidth() -> ring no longer spans full screen width
            .aspectRatio(1f)
            .padding(12.dp), // was 24.dp
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxWidth().aspectRatio(1f)) {
            val strokeWidth = 10.dp.toPx() // was 14.dp
            val gapAngleDegrees = 3f // gap between dashes
            val segmentSweep = (360f / totalSegments) - gapAngleDegrees

            val diameter = size.minDimension - strokeWidth
            val topLeft = androidx.compose.ui.geometry.Offset(
                (size.width - diameter) / 2f,
                (size.height - diameter) / 2f
            )
            val arcSize = Size(diameter, diameter)

            var startAngle = -90f // start from top, like clock 12 o'clock
            repeat(totalSegments) { index ->
                drawArc(
                    color = if (index < filledSegments) AccentMint else TextSecondary,
                    startAngle = startAngle,
                    sweepAngle = segmentSweep,
                    useCenter = false,
                    topLeft = topLeft,
                    size = arcSize,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                )
                startAngle += 360f / totalSegments
            }
        }

        // Center content
        Box(
            modifier = Modifier.padding(20.dp), // was 48.dp
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.foundation.layout.Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Filled.NightsStay,
                    contentDescription = null,
                    tint = AccentMint,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "Recommended bedtime",
                    color = TextSecondary,
                    fontSize = 13.sp
                )
                Text(
                    text = bedtimeText,
                    color = TextPrimary,
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "$cyclesText  ·  $durationText",
                    color = TextSecondary,
                    fontSize = 13.sp
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF202224)
@Composable
private fun SleepCycleRingPreview() {
    SleepCycleRing(
        bedtimeText = "10:00 PM",
        cyclesText = "6 sleep cycles",
        durationText = "9h 00m"
    )
}