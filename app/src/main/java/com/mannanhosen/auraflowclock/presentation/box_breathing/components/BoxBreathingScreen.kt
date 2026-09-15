package com.mannanhosen.auraflowclock.presentation.box_breathing


import androidx.compose.animation.AnimatedContent

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mannanhosen.auraflowclock.presentation.box_breathing.components.BoxBreathingEvent
import com.mannanhosen.auraflowclock.presentation.box_breathing.components.BoxBreathingPhase
import com.mannanhosen.auraflowclock.presentation.box_breathing.components.BoxBreathingState
import com.mannanhosen.auraflowclock.presentation.box_breathing.components.BoxBreathingViewModel

private val BackgroundColor = Color(0xFF101113)
private val SquareTrackColor = Color(0xFF2A2D30)
private val AccentTeal = Color(0xFF22D3EE)
private val AccentGreen = Color(0xFF34D399)
private val AccentAmber = Color(0xFFF59E0B)
private val MutedGray = Color(0xFF9E9E9E)
private val FieldBorderColor = Color(0xFF3A3D40)
private val FieldBackgroundColor = Color(0xFF1A1C1E)

private const val PHASE_SECONDS_MIN = 0
private const val PHASE_SECONDS_MAX = 1

private const val SESSION_MINUTE_MIN = 1
private const val SESSION_MINUTE_MAX = 30

@Composable
fun BoxBreathingScreen(
    viewModel: BoxBreathingViewModel = hiltViewModel()
) {
    BoxBreathingContent(state = viewModel.state, onEvent = viewModel::onEvent)
}

@Composable
fun BoxBreathingContent(
    state: BoxBreathingState,
    onEvent: (BoxBreathingEvent) -> Unit
) {
    val haptics = LocalHapticFeedback.current

    LaunchedEffect(state.currentPhase) {
        if (state.isRunning) {
            haptics.performHapticFeedback(HapticFeedbackType.TextHandleMove)
        }
    }

    LaunchedEffect(state.countdownValue, state.isCountingDown) {
        if (state.isCountingDown) {
            haptics.performHapticFeedback(HapticFeedbackType.TextHandleMove)
        }
    }

    val isSessionActive = state.isRunning || state.isPaused || state.isCountingDown || state.isSessionComplete

    Scaffold(containerColor = BackgroundColor) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundColor)
                .padding(padding)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(12.dp))
            Text("Box Breathing", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
            Text(
                "Guided Breathing",
                color = MutedGray,
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            AnimatedContent(
                targetState = isSessionActive,
                label = "screen_content_transition"
            ) { active ->
                if (!active) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        SettingsFields(state = state, onEvent = onEvent)
                        Spacer(modifier = Modifier.height(24.dp))
                        Button(
                            onClick = { onEvent(BoxBreathingEvent.StartPause) },
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF2B3A55),
                                contentColor = Color.White
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp)
                        ) {
                            Text(
                                text = "Start Breathing Session",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                } else {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(20.dp))
                        AnimatedContent(
                            targetState = when {
                                state.isCountingDown -> MainVisual.COUNTDOWN
                                state.isSessionComplete -> MainVisual.COMPLETE
                                else -> MainVisual.BREATHING
                            },
                            transitionSpec = { fadeIn(tween(250)) togetherWith fadeOut(tween(200)) },
                            label = "main_visual"
                        ) { visual ->
                            when (visual) {
                                MainVisual.COUNTDOWN -> CountdownDisplay(state.countdownValue)
                                MainVisual.COMPLETE -> CompletionCelebration(cycleCount = state.cycleCount)
                                MainVisual.BREATHING -> BreathingSquare(state = state)
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        if (!state.isCountingDown) {
                            Text(
                                text = "Cycle ${state.cycleCount} · ${state.sessionSecondsElapsed / 60}:${
                                    (state.sessionSecondsElapsed % 60).toString().padStart(2, '0')
                                } / ${state.sessionDurationSeconds / 60}:${
                                    (state.sessionDurationSeconds % 60).toString().padStart(2, '0')
                                }",
                                color = MutedGray,
                                fontSize = 13.sp,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            if (isSessionActive) {
                Controls(state = state, onEvent = onEvent)
                Spacer(modifier = Modifier.height(28.dp))
            }
        }
    }
}

private enum class MainVisual { COUNTDOWN, BREATHING, COMPLETE }

@Composable
private fun SettingsFields(
    state: BoxBreathingState,
    onEvent: (BoxBreathingEvent) -> Unit
) {
    val enabled = !state.isRunning && !state.isCountingDown

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            PhaseStepperField(
                label = "Inhale Time (seconds)",
                value = state.inhaleSeconds,
                enabled = enabled,
                modifier = Modifier.weight(1f),
                onValueChange = { onEvent(BoxBreathingEvent.SetInhaleSeconds(it)) }
            )
            PhaseStepperField(
                label = "Hold Time (seconds)",
                value = state.holdFullSeconds,
                enabled = enabled,
                modifier = Modifier.weight(1f),
                onValueChange = { onEvent(BoxBreathingEvent.SetHoldFullSeconds(it)) }
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            PhaseStepperField(
                label = "Exhale Time (seconds)",
                value = state.exhaleSeconds,
                enabled = enabled,
                modifier = Modifier.weight(1f),
                onValueChange = { onEvent(BoxBreathingEvent.SetExhaleSeconds(it)) }
            )
            PhaseStepperField(
                label = "Pause Time (seconds)",
                value = state.holdEmptySeconds,
                enabled = enabled,
                modifier = Modifier.weight(1f),
                onValueChange = { onEvent(BoxBreathingEvent.SetHoldEmptySeconds(it)) }
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        val currentMinutes = state.sessionDurationSeconds / 60
        PhaseStepperField(
            label = "Session Length (minutes)",
            value = currentMinutes,
            enabled = enabled,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { newMinutes ->
                onEvent(BoxBreathingEvent.SetSessionDuration(newMinutes * 60))
            },
            minValue = SESSION_MINUTE_MIN,
            maxValue = SESSION_MINUTE_MAX
        )
    }
}

@Composable
private fun PhaseStepperField(
    label: String,
    value: Int,
    enabled: Boolean,
    modifier: Modifier = Modifier,
    minValue: Int = PHASE_SECONDS_MIN,
    maxValue: Int = PHASE_SECONDS_MAX,
    onValueChange: (Int) -> Unit
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            color = AccentTeal,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(6.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .border(1.dp, FieldBorderColor, RoundedCornerShape(10.dp))
                .background(FieldBackgroundColor, RoundedCornerShape(10.dp))
        ) {
            Text(
                text = value.toString(),
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.align(Alignment.Center)
            )

            Column(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = "Increase $label",
                    tint = if (enabled) MutedGray else MutedGray.copy(alpha = 0.35f),
                    modifier = Modifier
                        .size(16.dp)
                        .clickable(enabled = enabled) {
                            if (value < maxValue) onValueChange(value + 1)
                        }
                )
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Decrease $label",
                    tint = if (enabled) MutedGray else MutedGray.copy(alpha = 0.35f),
                    modifier = Modifier
                        .size(16.dp)
                        .clickable(enabled = enabled) {
                            if (value > minValue) onValueChange(value - 1)
                        }
                )
            }
        }
    }
}


@Composable
private fun BreathingSquare(state: BoxBreathingState) {
    val sizeDp = 220.dp
    val strokeWidth = 8.dp

    // ✅ Animatable diye manually control kora hocche, jate phase change
    // hoile age-r value theke back-track na kore notun phase 0 theke
    // shuru hoy ebong protita second dhore smoothly target porjonto egiye jay.
    val animatedPhaseProgress = remember { Animatable(0f) }

    LaunchedEffect(state.currentPhase, state.phaseSecondsLeft) {
        animatedPhaseProgress.animateTo(
            targetValue = state.phaseProgress,
            animationSpec = tween(durationMillis = 900, easing = LinearEasing)
        )
    }

    // ✅ notun phase shuru hobar shathe shathe animatedPhaseProgress ke
    // shunno-te snap kore dao (age-r phase-er leftover value clear kore),
    // tarpor upor-er LaunchedEffect take target porjonto animate korte dao.
    LaunchedEffect(state.currentPhase) {
        animatedPhaseProgress.snapTo(0f)
    }

    Box(contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.size(sizeDp)) {
            val strokePx = strokeWidth.toPx()
            val inset = strokePx
            val w = size.width
            val h = size.height

            // background - pura square-er dim track
            drawRoundRect(
                color = SquareTrackColor,
                topLeft = Offset(inset, inset),
                size = Size(w - 2* inset, h - 2* inset),
                cornerRadius = CornerRadius(28f, 28f),
                style = Stroke(width = strokePx, cap = StrokeCap.
                Round)
            )

            val topLeftPt = Offset(inset,  inset)
            val topRightPt = Offset(w - inset, inset)
            val bottomRightPt = Offset(  w- inset, h - inset)
            val bottomLeftPt = Offset(w- inset, h - inset)

            // TOP(0)->RIGHT(1)->BOTTOM(2)->LEFT(3) - ekta continuous loop, clockwise
            val edgeStarts = listOf(topLeftPt, topRightPt, bottomRightPt, bottomLeftPt)
            val edgeEnds = listOf(topRightPt, bottomRightPt, bottomLeftPt, topLeftPt)

            val currentPhaseIndex = when (state.currentPhase) {
                BoxBreathingPhase.INHALE -> 0
                BoxBreathingPhase.HOLD_FULL -> 1
                BoxBreathingPhase.EXHALE -> 2
                BoxBreathingPhase.HOLD_EMPTY -> 3
            }

            // ✅ age shesh hoye jawa phase gula-r pash pura lit thake
            for (i in 0 until currentPhaseIndex) {
                drawLine(
                    color = AccentTeal,
                    start = edgeStarts[i],
                    end = edgeEnds[i],
                    strokeWidth = strokePx,
                    cap = StrokeCap.Round
                )
            }

            // ✅ current phase-er pash partially draw hocche, progress onujayi
            val currentStart = edgeStarts[currentPhaseIndex]
            val currentEnd = edgeEnds[currentPhaseIndex]
            val partialEnd = Offset(
                x = currentStart.x + (currentEnd.x - currentStart.x) * animatedPhaseProgress.value,
                y = currentStart.y + (currentEnd.y - currentStart.y) * animatedPhaseProgress.value
            )
            drawLine(
                color = AccentTeal,
                start = currentStart,
                end = partialEnd,
                strokeWidth = strokePx,
                cap = StrokeCap.Round
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            AnimatedContent(
                targetState = state.currentPhase,
                transitionSpec = { fadeIn(tween(200)) togetherWith fadeOut(tween(150)) },
                label = "phase_label"
            ) { phase ->
                Text(text = phase.label, color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Light)
            }
            Text(
                text = "${state.phaseSecondsLeft}",
                color = AccentTeal,
                fontSize = 38.sp,
                fontWeight = FontWeight.Light,
                fontFamily = FontFamily.Monospace
            )
        }
    }
}

@Composable
private fun CountdownDisplay(value: Int) {
    Box(
        modifier = Modifier
            .size(220.dp)
            .border(2.dp, AccentTeal.copy(alpha = 0.4f), RoundedCornerShape(28.dp)),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Get ready", color = MutedGray, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))
            AnimatedContent(
                targetState = value,
                transitionSpec = {
                    (scaleIn(initialScale = 0.4f) + fadeIn()) togetherWith (scaleOut(targetScale = 1.6f) + fadeOut())
                },
                label = "countdown_number"
            ) { v ->
                Text(text = "$v", color = AccentTeal, fontSize = 72.sp, fontWeight = FontWeight.Light)
            }
        }
    }
}

@Composable
private fun CompletionCelebration(cycleCount: Int) {
    val scale = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
    }

    Box(
        modifier = Modifier
            .size(220.dp)
            .border(2.dp, AccentGreen.copy(alpha = 0.5f), RoundedCornerShape(28.dp)),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Session\nComplete!",
                color = AccentGreen,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(12.dp))
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "Session complete",
                tint = AccentGreen,
                modifier = Modifier
                    .size(56.dp)
                    .graphicsLayer(scaleX = scale.value, scaleY = scale.value)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "$cycleCount cycles completed",
                color = MutedGray,
                fontSize = 13.sp
            )
        }
    }
}

@Composable
private fun Controls(
    state: BoxBreathingState,
    onEvent: (BoxBreathingEvent) -> Unit
) {
    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        val showStop = !state.isCountingDown &&
                (state.isRunning || state.isPaused || state.isSessionComplete)

        if (showStop) {
            OutlinedButton(
                onClick = { onEvent(BoxBreathingEvent.Stop) },
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = AccentAmber),
                modifier = Modifier.height(56.dp)
            ) {
                Icon(Icons.Default.Stop, contentDescription = "Stop")
                Spacer(modifier = Modifier.width(6.dp))
                Text("Stop", fontWeight = FontWeight.SemiBold)
            }
        }

        Button(
            onClick = { onEvent(BoxBreathingEvent.StartPause) },
            enabled = !state.isCountingDown,
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(containerColor = AccentGreen, contentColor = Color(0xFF10120F)),
            modifier = Modifier.height(56.dp)
        ) {
            Icon(if (state.isRunning) Icons.Default.Pause else Icons.Default.PlayArrow, contentDescription = null)
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = when {
                    state.isRunning -> "Pause"
                    state.isSessionComplete -> "New Session"
                    state.isPaused -> "Resume"
                    else -> "Start"
                },
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4_XL)
@Composable
fun PreviewBoxBreathingRunning() {
    BoxBreathingContent(
        state = BoxBreathingState(
            isRunning = true,
            currentPhase = BoxBreathingPhase.HOLD_FULL,
            phaseSecondsLeft = 2,
            inhaleSeconds = 4,
            holdFullSeconds = 4,
            cycleCount = 3,
            sessionSecondsElapsed = 62
        ),
        onEvent = {}
    )
}

@Preview(showBackground = true, device = Devices.PIXEL_4_XL)
@Composable
fun PreviewBoxBreathingIdle() {
    BoxBreathingContent(state = BoxBreathingState(), onEvent = {})
}

@Preview(showBackground = true, device = Devices.PIXEL_4_XL)
@Composable
fun PreviewBoxBreathingComplete() {
    BoxBreathingContent(
        state = BoxBreathingState(isSessionComplete = true, cycleCount = 18, sessionSecondsElapsed = 300),
        onEvent = {}
    )
}