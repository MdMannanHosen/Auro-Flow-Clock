package com.mannanhosen.auraflowclock.presentation.worldclock.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.checkerframework.checker.units.qual.s
import java.time.LocalTime
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AnalogClock(
    time: LocalTime,
    modifier: Modifier = Modifier,
    clockSize : Dp = 80.dp,
    clockColor : Color = Color.Black,
    handColor : Color = Color.Black,
    secondHandColor : Color = Color.Red,
    selected : Boolean = false
) {
    val effectiveClockColor = if (selected) Color(0xFF1976D2) else clockColor
    val density = LocalDensity.current

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Canvas(modifier = Modifier.size(clockSize)) {
            val radius = size.minDimension / 2f
            val center = Offset(size.width / 2f, size.height / 2f)

            // Draw outer ring
            drawCircle(
                color = effectiveClockColor.copy(alpha = 0.1f),
                radius = radius,
                center = center
            )

            // Draw main clock circle with gradient effect
            drawCircle(
                color = effectiveClockColor.copy(alpha = 0.05f),
                radius = radius * 0.95f,
                center = center
            )
            drawCircle(
                color = effectiveClockColor,
                radius = radius * 0.95f,
                center = center,
                style = Stroke(width = 2.dp.toPx())
            )

            // Draw minute markers (60 small lines)
            for (i in 0..59) {
                val angle = (i * 6f - 90f) * (PI / 180f)
                val isHourMarker = i % 5 == 0
                val startRadius = if (isHourMarker) radius * 0.80f else radius * 0.87f
                val endRadius = radius * 0.92f
                val strokeWidth = if (isHourMarker) 2.dp.toPx() else 0.8.dp.toPx()

                drawLine(
                    color = effectiveClockColor.copy(alpha = if (isHourMarker) 0.9f else 0.4f),
                    start = Offset(
                        x = center.x + (cos(angle) * startRadius).toFloat(),
                        y = center.y + (sin(angle) * startRadius).toFloat()
                    ),
                    end = Offset(
                        x = center.x + (cos(angle) * endRadius).toFloat(),
                        y = center.y + (sin(angle) * endRadius).toFloat()
                    ),
                    strokeWidth = strokeWidth,
                    cap = StrokeCap.Round
                )
            }

            // Draw hour numbers
            for (i in 1..12) {
                val angle = (i * 30f - 90f) * (PI / 180f)
                val numberRadius = radius * 0.70f
                val x = center.x + (cos(angle) * numberRadius).toFloat()
                val y = center.y + (sin(angle) * numberRadius).toFloat()

                drawContext.canvas.nativeCanvas.apply {
                    val paint = android.graphics.Paint().apply {
                        color = effectiveClockColor.toArgb()
                        textAlign = android.graphics.Paint.Align.CENTER
                        textSize = with(density) { 12.sp.toPx() }
                        typeface = android.graphics.Typeface.DEFAULT_BOLD
                        isAntiAlias = true
                    }
                    drawText(
                        i.toString(),
                        x,
                        y + paint.textSize / 3,
                        paint
                    )
                }
            }

            // Draw hands with improved styling
            val hour = time.hour % 12
            val minute = time.minute
            val second = time.second

            val hourAngle = ((hour + minute / 60f) * 30f - 90f) * (PI / 180f)
            val minuteAngle = ((minute + second / 60f) * 6f - 90f) * (PI / 180f)
            val secondAngle = (second * 6f - 90f) * (PI / 180f)

            // Hour hand with shadow
            drawLine(
                color = Color.Black.copy(alpha = 0.2f),
                start = Offset(center.x + 1.dp.toPx(), center.y + 1.dp.toPx()),
                end = Offset(
                    x = center.x + (cos(hourAngle) * radius * 0.5f).toFloat() + 1.dp.toPx(),
                    y = center.y + (sin(hourAngle) * radius * 0.5f).toFloat() + 1.dp.toPx()
                ),
                strokeWidth = 5.dp.toPx(),
                cap = StrokeCap.Round
            )
            drawLine(
                color = handColor,
                start = center,
                end = Offset(
                    x = center.x + (cos(hourAngle) * radius * 0.5f).toFloat(),
                    y = center.y + (sin(hourAngle) * radius * 0.5f).toFloat()
                ),
                strokeWidth = 4.5.dp.toPx(),
                cap = StrokeCap.Round
            )

            // Minute hand with shadow
            drawLine(
                color = Color.Black.copy(alpha = 0.2f),
                start = Offset(center.x + 1.dp.toPx(), center.y + 1.dp.toPx()),
                end = Offset(
                    x = center.x + (cos(minuteAngle) * radius * 0.75f).toFloat() + 1.dp.toPx(),
                    y = center.y + (sin(minuteAngle) * radius * 0.75f).toFloat() + 1.dp.toPx()
                ),
                strokeWidth = 3.dp.toPx(),
                cap = StrokeCap.Round
            )
            drawLine(
                color = handColor,
                start = center,
                end = Offset(
                    x = center.x + (cos(minuteAngle) * radius * 0.75f).toFloat(),
                    y = center.y + (sin(minuteAngle) * radius * 0.75f).toFloat()
                ),
                strokeWidth = 2.5.dp.toPx(),
                cap = StrokeCap.Round
            )

            // Second hand (thinner and longer)
            drawLine(
                color = secondHandColor,
                start = Offset(
                    x = center.x - (cos(secondAngle) * radius * 0.2f).toFloat(),
                    y = center.y - (sin(secondAngle) * radius * 0.2f).toFloat()
                ),
                end = Offset(
                    x = center.x + (cos(secondAngle) * radius * 0.85f).toFloat(),
                    y = center.y + (sin(secondAngle) * radius * 0.85f).toFloat()
                ),
                strokeWidth = 1.5.dp.toPx(),
                cap = StrokeCap.Round
            )

            // Center dot with multiple layers
            drawCircle(
                color = Color.Black.copy(alpha = 0.1f),
                radius = 6.dp.toPx(),
                center = Offset(center.x + 0.5.dp.toPx(), center.y + 0.5.dp.toPx())
            )
            drawCircle(
                color = secondHandColor,
                radius = 5.dp.toPx(),
                center = center
            )
            drawCircle(
                color = handColor,
                radius = 3.dp.toPx(),
                center = center
            )
            drawCircle(
                color = Color.White,
                radius = 1.5.dp.toPx(),
                center = center
            )
        }
    }
}
