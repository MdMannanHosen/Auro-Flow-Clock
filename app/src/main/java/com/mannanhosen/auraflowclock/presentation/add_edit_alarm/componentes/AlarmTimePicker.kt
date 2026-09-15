package com.mannanhosen.auraflowclock.presentation.alarm.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun AlarmTimePicker(
    hours: List<String>,
    minutes: List<String>,
    periods: List<String>,
    hourState: PagerState,
    minutesState: PagerState,
    periodState: PagerState,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        TimeWheelPicker(
            items = hours,
            state = hourState,
            modifier = Modifier.weight(1f),
            isInfinite = true
        )
        Text(
            text = ":",
            style = TextStyle(
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFDDDAD5)
            )
        )
        Spacer(modifier = Modifier.width(12.dp))
        TimeWheelPicker(
            items = minutes,
            state = minutesState,
            modifier = Modifier.weight(1f),
            isInfinite = true
        )
        Spacer(modifier = Modifier.width(12.dp))
        TimeWheelPicker(
            items = periods,
            state = periodState,
            modifier = Modifier.weight(0.8f),
            isInfinite = false
        )
    }
}
