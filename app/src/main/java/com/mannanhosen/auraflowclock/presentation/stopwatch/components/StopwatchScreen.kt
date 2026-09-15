package com.mannanhosen.auraflowclock.presentation.stopwatch.components
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mannanhosen.auraflowclock.data.model.StopwatchLap

private val BackgroundColor = Color(0xFF202224)
private val AccentGreen = Color(0xFF4CAF50)
private val AccentRed = Color(0xFFE53935)
private val CardColor = Color(0xFF2C2E30)
private val BestLapColor = Color(0xFF4CAF50)
private val WorstLapColor = Color(0xFFE53935)
private val MutedGray = Color(0xFF9E9E9E)
private val DisabledGray = Color(0xFF616161)

@Composable
fun StopwatchScreen(
    viewModel: StopwatchViewModel = hiltViewModel()
) {
    StopwatchContent(
        state = viewModel.state,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun StopwatchContent(
    state: StopwatchState,
    onEvent: (StopwatchEvent) -> Unit
) {
    Scaffold(containerColor = BackgroundColor) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundColor)
                .padding(padding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(56.dp))

            // ⏱️ Centisecond soho boro time display - onno app er tulonay onek beshi precise
            Text(
                text = StopwatchTimeFormatter.format(state.elapsedMillis),
                color = Color.White,
                fontSize = 56.sp,
                fontWeight = FontWeight.Light,
                fontFamily = FontFamily.Monospace
            )

            Spacer(modifier = Modifier.height(40.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // ⬅️ Bam button: cholte thakle "Lap", theme thakle "Reset"
                StopwatchActionButton(
                    icon = if (state.isRunning) Icons.Default.Flag else Icons.Default.Refresh,
                    label = if (state.isRunning) "Lap" else "Reset",
                    ringColor = DisabledGray,
                    contentColor = Color.White,
                    enabled = state.isRunning || state.elapsedMillis > 0L,
                    onClick = {
                        onEvent(if (state.isRunning) StopwatchEvent.Lap else StopwatchEvent.Reset)
                    }
                )

                // ➡️ Dan button: Start / Pause
                StopwatchActionButton(
                    icon = if (state.isRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
                    label = if (state.isRunning) "Pause" else "Start",
                    ringColor = if (state.isRunning) AccentRed else AccentGreen,
                    contentColor = if (state.isRunning) AccentRed else AccentGreen,
                    enabled = true,
                    onClick = { onEvent(StopwatchEvent.StartPause) }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            if (state.laps.isNotEmpty()) {
                LapHeader()
                Spacer(modifier = Modifier.height(8.dp))
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items(state.laps, key = { it.id }) { lap ->
                        LapRow(
                            lap = lap,
                            isBest = lap.id == state.bestLapId,
                            isWorst = lap.id == state.worstLapId
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun StopwatchActionButton(
    icon: ImageVector,
    label: String,
    ringColor: Color,
    contentColor: Color,
    enabled: Boolean,
    onClick: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Surface(
            shape = CircleShape,
            color = Color.Transparent,
            border = BorderStroke(1.5.dp, if (enabled) ringColor else Color(0xFF424242)),
            modifier = Modifier.size(72.dp)
        ) {
            IconButton(onClick = onClick, enabled = enabled) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = if (enabled) contentColor else DisabledGray
                )
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = label,
            color = if (enabled) Color.White else DisabledGray,
            fontSize = 12.sp
        )
    }
}

@Composable
private fun LapHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Lap", color = MutedGray, fontSize = 12.sp)
        Text("Split", color = MutedGray, fontSize = 12.sp)
        Text("Total", color = MutedGray, fontSize = 12.sp)
    }
}

@Composable
private fun LapRow(
    lap: StopwatchLap,
    isBest: Boolean,
    isWorst: Boolean
) {
    val splitColor = when {
        isBest -> BestLapColor
        isWorst -> WorstLapColor
        else -> Color.White
    }

    Surface(
        shape = MaterialTheme.shapes.medium,
        color = CardColor,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Lap ${lap.lapNumber}", color = Color(0xFFBDBDBD), fontSize = 14.sp)
            Text(
                text = StopwatchTimeFormatter.format(lap.lapTimeMillis),
                color = splitColor,
                fontFamily = FontFamily.Monospace,
                fontSize = 14.sp,
                fontWeight = if (isBest || isWorst) FontWeight.Bold else FontWeight.Normal
            )
            Text(
                text = StopwatchTimeFormatter.format(lap.totalTimeMillis),
                color = MutedGray,
                fontFamily = FontFamily.Monospace,
                fontSize = 14.sp
            )
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4_XL)
@Composable
fun PreviewStopwatchScreen() {
    StopwatchContent(
        state = StopwatchState(
            elapsedMillis = 75340L,
            isRunning = true,
            laps = listOf(
                StopwatchLap(id = 3, sessionId = 1L, lapNumber = 3, lapTimeMillis = 18230L, totalTimeMillis = 75340L),
                StopwatchLap(id = 2, sessionId = 1L, lapNumber = 2, lapTimeMillis = 30110L, totalTimeMillis = 57110L),
                StopwatchLap(id = 1, sessionId = 1L, lapNumber = 1, lapTimeMillis = 27000L, totalTimeMillis = 27000L)
            ),
            bestLapId = 3,
            worstLapId = 2
        ),
        onEvent = {}
    )
}