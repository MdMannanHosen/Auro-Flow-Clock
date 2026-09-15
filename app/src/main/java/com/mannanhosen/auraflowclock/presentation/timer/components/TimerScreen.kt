package com.mannanhosen.auraflowclock.presentation.timer.components
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.togetherWith
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.progressBarRangeInfo
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

private val BackgroundColor = Color(0xFF101113)
private val RingTrackColor = Color(0xFF2A2D30)
private val AccentGreen = Color(0xFF34D399)
private val AccentTeal = Color(0xFF22D3EE)
private val AccentAmber = Color(0xFFF59E0B)
private val AccentRed = Color(0xFFEF4444)
private val MutedGray = Color(0xFF9E9E9E)

private val presetsMinutes = listOf(1, 5, 15, 30, 60)

@Composable
fun TimerScreen(
    viewModel: TimerViewModel = hiltViewModel()
) {
    TimerContent(
        state = viewModel.state,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun TimerContent(
    state: TimerState,
    onEvent: (TimerEvent) -> Unit
) {
    val haptics = LocalHapticFeedback.current

    Scaffold(containerColor = BackgroundColor) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundColor)
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            // ✅ preset chips - idle/paused obosthay enabled, running obosthay disabled
            PresetChipsRow(
                enabled = !state.isRunning,
                onPresetSelected = { minutes ->
                    haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                    onEvent(TimerEvent.SelectPreset(minutes))
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            TimerRing(
                state = state,
                onDragMinutes = { deltaMinutes ->
                    haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                    onEvent(TimerEvent.AdjustDuration(deltaMinutes))
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            TimerControls(
                state = state,
                onEvent = { event ->
                    haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                    onEvent(event)
                }
            )

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
private fun PresetChipsRow(
    enabled: Boolean,
    onPresetSelected: (Int) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(presetsMinutes) { minutes ->
            val label = if (minutes >= 60) "${minutes / 60}h" else "${minutes}m"
            Surface(
                shape = RoundedCornerShape(50),
                color = if (enabled) Color(0xFF1D1F21) else Color(0xFF1D1F21).copy(alpha = 0.4f),
                onClick = { onPresetSelected(minutes) },
                enabled = enabled
            ) {
                Text(
                    text = label,
                    color = if (enabled) Color.White else MutedGray,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(horizontal = 18.dp, vertical = 10.dp)
                )
            }
        }
    }
}

@Composable
private fun TimerRing(
    state: TimerState,
    onDragMinutes: (Int) -> Unit
) {
    val ringSize = 260.dp
    val strokeWidth = 16.dp

    val animatedProgress by animateFloatAsState(
        targetValue = state.progressFraction,
        animationSpec = tween(durationMillis = if (state.isRunning) 200 else 350),
        label = "timer_progress"
    )

    // ✅ running obosthay ring-er glow subtly pulse kore - "live" onubhuti dewar jonno
    val infiniteTransition = rememberInfiniteTransition(label = "glow_pulse")
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.25f,
        targetValue = 0.55f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow_alpha"
    )

    var dragAccumulatorPx by remember { mutableFloatStateOf(0f) }
    val dragThresholdPx = 40.dp

    Box(
        modifier = Modifier
            .size(ringSize)
            .semantics {
                contentDescription = "Timer, ${TimerTimeFormatter.format(state.remainingMillis)} remaining"
                progressBarRangeInfo = ProgressBarRangeInfo(state.progressFraction, 0f..1f)
            }
            .pointerInput(state.isRunning) {
                if (!state.isRunning) {
                    detectVerticalDragGestures(
                        onDragStart = { dragAccumulatorPx = 0f },
                        onDragEnd = { dragAccumulatorPx = 0f }
                    ) { change, dragAmount ->
                        change.consume()
                        dragAccumulatorPx -= dragAmount // upore drag = time barbe
                        val thresholdPx = dragThresholdPx.toPx()
                        while (dragAccumulatorPx >= thresholdPx) {
                            onDragMinutes(1)
                            dragAccumulatorPx -= thresholdPx
                        }
                        while (dragAccumulatorPx <= -thresholdPx) {
                            onDragMinutes(-1)
                            dragAccumulatorPx += thresholdPx
                        }
                    }
                }
            },
        contentAlignment = Alignment.Center
    ) {
        // ✅ soft glow - running obosthay pulse kore, blur API 31+ dorkar hoy na
        if (state.isRunning) {
            Canvas(modifier = Modifier.size(ringSize + 40.dp)) {
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(AccentGreen.copy(alpha = glowAlpha), Color.Transparent)
                    )
                )
            }
        }

        Canvas(modifier = Modifier.size(ringSize)) {
            val stroke = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
            val inset = strokeWidth.toPx() / 2
            val arcSize = Size(size.width - strokeWidth.toPx(), size.height - strokeWidth.toPx())
            val topLeft = Offset(inset, inset)

            // background track
            drawArc(
                color = RingTrackColor,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = stroke
            )

            // progress arc
            drawArc(
                brush = Brush.sweepGradient(listOf(AccentTeal, AccentGreen, AccentTeal)),
                startAngle = -90f,
                sweepAngle = 360f * animatedProgress,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = stroke
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = TimerTimeFormatter.format(state.remainingMillis),
                color = Color.White,
                fontSize = 48.sp,
                fontWeight = FontWeight.Light,
                fontFamily = FontFamily.Monospace
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = when {
                    state.isRunning -> "Running"
                    state.isPaused -> "Paused"
                    else -> "Drag ring to adjust"
                },
                color = MutedGray,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
private fun TimerControls(
    state: TimerState,
    onEvent: (TimerEvent) -> Unit
) {
    AnimatedContent(
        targetState = when {
            state.isRunning -> ControlsPhase.RUNNING
            state.isPaused -> ControlsPhase.PAUSED
            else -> ControlsPhase.IDLE
        },
        transitionSpec = {
            (slideInVertically { it / 2 } + fadeIn()) togetherWith (slideOutVertically { -it / 2 } + fadeOut())
        },
        label = "controls_phase"
    ) { phase ->
        when (phase) {
            ControlsPhase.IDLE -> {
                PrimaryPillButton(
                    text = "Start",
                    icon = Icons.Default.PlayArrow,
                    color = AccentGreen,
                    onClick = { onEvent(TimerEvent.StartPause) }
                )
            }

            ControlsPhase.RUNNING -> {
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    OutlinedPillButton(
                        text = "+1 min",
                        icon = Icons.Default.Add,
                        color = AccentTeal,
                        onClick = { onEvent(TimerEvent.AddOneMinute) }
                    )
                    PrimaryPillButton(
                        text = "Pause",
                        icon = Icons.Default.Pause,
                        color = AccentAmber,
                        onClick = { onEvent(TimerEvent.StartPause) }
                    )
                }
            }

            ControlsPhase.PAUSED -> {
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    OutlinedPillButton(
                        text = "Reset",
                        icon = Icons.Default.RestartAlt,
                        color = AccentRed,
                        onClick = { onEvent(TimerEvent.Reset) }
                    )
                    PrimaryPillButton(
                        text = "Resume",
                        icon = Icons.Default.PlayArrow,
                        color = AccentGreen,
                        onClick = { onEvent(TimerEvent.StartPause) }
                    )
                }
            }
        }
    }
}

private enum class ControlsPhase { IDLE, RUNNING, PAUSED }

@Composable
private fun PrimaryPillButton(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(containerColor = color, contentColor = Color(0xFF10120F)),
        modifier = Modifier.height(56.dp)
    ) {
        Icon(icon, contentDescription = text)
        Spacer(modifier = Modifier.width(6.dp))
        Text(text, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
    }
}

@Composable
private fun OutlinedPillButton(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = color),
        modifier = Modifier.height(56.dp)
    ) {
        Icon(icon, contentDescription = text)
        Spacer(modifier = Modifier.width(6.dp))
        Text(text, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4_XL)
@Composable
fun PreviewTimerScreenIdle() {
    TimerContent(state = TimerState(), onEvent = {})
}

@Preview(showBackground = true, device = Devices.PIXEL_4_XL)
@Composable
fun PreviewTimerScreenRunning() {
    TimerContent(
        state = TimerState(
            totalDurationMillis = 5 * 60_000L,
            remainingMillis = 3 * 60_000L + 12_000L,
            isRunning = true
        ),
        onEvent = {}
    )
}