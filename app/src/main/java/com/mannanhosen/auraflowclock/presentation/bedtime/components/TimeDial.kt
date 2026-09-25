package com.mannanhosen.auraflowclock.presentation.bedtime.components
import android.R
import android.R.attr.radius
import android.R.attr.strokeWidth
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Icon
import androidx.compose.material3.SliderDefaults.Track
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.cos
import kotlin.math.sin

private val AppBackground = Color(0xFF0B0F14)

// Cards / Bottom Sheets
private val CardBackground = Color(0xFF25282B)

// Primary Text / Icons
private val TextPrimary = Color(0xFFDDDAD5)

// Secondary Text
private val TextSecondary = Color(0xFF9CA3AF)

// Accent
private val AccentMint = Color(0xFF00BFA5)


@Composable
 fun TimeDial(
    hour: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center

    ) {
        Box(
            modifier = Modifier.fillMaxSize()
                .aspectRatio(1f)

        ){
            Canvas(
            modifier = Modifier.fillMaxSize()
                .padding(32.dp)
        ) {
            val r = size.minDimension / 2f
            val c = center

            // Outer circular track
            drawCircle(
                color = CardBackground,
                radius = r,
                style = Stroke(50f)
            )

            // Tick marks
            for (i in 0 until 12) {
                val a = Math.toRadians(i * 30.0)
                val major = i % 3 == 0
                val r1 = r - if (major) 16f else 9f
                drawLine(
                    color = TextPrimary,
                    Offset(c.x + r1 * sin(a).toFloat(), c.y - r1 * cos(a).toFloat()),
                    Offset(c.x + (r - 3f) * sin(a).toFloat(), c.y - (r - 3f) * cos(a).toFloat()),
                    strokeWidth = if (major) 4f else 2f
                )
                }

            // Selected hour position
            val hourAngle = Math.toRadians(
                (hour % 12) * 30.0
            )

            val dot = Offset(
                x = c.x + r * sin(hourAngle).toFloat(),
                y = c.y - r * cos(hourAngle).toFloat()
            )

            // Glow
            drawCircle(
                color = AccentMint.copy(alpha = 0.2f),
                radius = 48f,
                center = dot
            )

            // Selected dot
            drawCircle(
                color = AccentMint ,
                radius = 30f,
                center = dot
            )
        }


        // 12
        DialNumber(
            text = "12",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 16.dp)
        )

        // 6
        DialNumber(
            text = "6",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        )

        // 3
        DialNumber(
            text = "3",
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 8.dp)
        )

        // 9
        DialNumber(
            text = "9",
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 8.dp)
        )


        // Center content
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.Center)
        ) {

            Icon(
                imageVector = Icons.Filled.WbSunny,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "%d:00".format(hour),
                fontSize = 26.sp,
                color = Color.White,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

}


@Composable
private fun DialNumber(
    text: String,
    modifier: Modifier = Modifier.fillMaxWidth()

) {
    Text(
        text = text,
        color = TextPrimary,
        fontSize = 26.sp,
        fontWeight = FontWeight.SemiBold,
        textAlign = TextAlign.Center,
        modifier = modifier.padding(40.dp)
    )
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewDiTimeDial() {
    TimeDial(hour = 7)
}