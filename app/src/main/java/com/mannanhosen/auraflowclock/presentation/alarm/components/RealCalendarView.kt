package com.mannanhosen.auraflowclock.presentation.alarm.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Calendar
import java.util.Date

@Composable
fun RealCalendarView(
    onDateSelected: (Date) -> Unit,
    onDismiss: () -> Unit
) {
    val todayCalendar = Calendar.getInstance()
    val todayDate = todayCalendar.time

    var currentMonth by remember { mutableStateOf(todayCalendar.get(Calendar.MONTH)) }
    var currentYear by remember { mutableStateOf(todayCalendar.get(Calendar.YEAR)) }
    var selectedDay by remember { mutableStateOf(todayCalendar.get(Calendar.DAY_OF_MONTH)) }
    var selectedMonth by remember { mutableStateOf(todayCalendar.get(Calendar.MONTH)) }
    var selectedYear by remember { mutableStateOf(todayCalendar.get(Calendar.YEAR)) }

    val dayNames = listOf("S", "M", "T", "W", "T", "F", "S", "S")
    val tempCal = Calendar.getInstance().apply {
        set(Calendar.YEAR, currentYear)
        set(Calendar.MONTH, currentMonth)
        set(Calendar.DAY_OF_MONTH, 1)
    }

    val daysInMonth = tempCal.getActualMaximum(Calendar.DAY_OF_MONTH)
    val firstDayOfWeek = tempCal.get(Calendar.DAY_OF_WEEK) - 1

    Column(
        modifier = Modifier.fillMaxWidth().padding(5.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    if (currentMonth == 0) {
                        currentMonth = 11
                        currentYear--
                    } else {
                        currentMonth--
                    }
                }
            ) {
                Text("<", color = Color(0xFFE91E63), fontSize = 24.sp, fontWeight = FontWeight.Bold)
            }

            IconButton(
                onClick = {
                    if (currentMonth == 11) {
                        currentMonth = 0
                        currentYear++
                    } else {
                        currentMonth++
                    }
                }
            ) {
                Text(">", color = Color(0xFFE91E63), fontSize = 24.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            dayNames.forEach { day ->
                Text(
                    text = day,
                    color = Color(0xFFE91E63),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    modifier = Modifier.weight(1f),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column {
            for (row in 0 until 6) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    for (col in 0 until 7) {
                        val dayNumber = (row * 7) + col - firstDayOfWeek + 1
                        val isValidDay = dayNumber in 1..daysInMonth

                        val checkCalender = Calendar.getInstance().apply {
                            set(currentYear, currentMonth, dayNumber)
                        }

                        val isFutureOrToday = !checkCalender.time.before(todayDate)

                        if (isValidDay && isFutureOrToday) {
                            val isSelected = selectedDay == dayNumber &&
                                    selectedMonth == currentMonth &&
                                    selectedYear == currentYear

                            val isToday = dayNumber == todayCalendar.get(Calendar.DAY_OF_MONTH) &&
                                    currentMonth == todayCalendar.get(Calendar.MONTH) &&
                                    currentYear == todayCalendar.get(Calendar.YEAR)

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1f)
                                    .padding(2.dp)
                                    .clip(CircleShape)
                                    .clickable {
                                        selectedDay = dayNumber
                                        selectedMonth = currentMonth
                                        selectedYear = currentYear
                                    }
                                    .background(
                                        when {
                                            isSelected -> Color(0xFFE91E63)
                                            isToday -> Color(0xFFE91E63).copy(0.3f)
                                            else -> Color.Transparent
                                        }
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = dayNumber.toString(),
                                    color = when {
                                        isSelected -> Color.White
                                        isToday -> Color(0xFFE91E63)
                                        else -> Color.White.copy(0.7f)
                                    },
                                    fontWeight = if (isSelected || isToday) FontWeight.Bold else FontWeight.Normal,
                                    fontSize = 14.sp
                                )
                            }
                        } else if (isValidDay) {
                            Box(
                                modifier = Modifier.weight(1f).aspectRatio(1f).padding(2.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = dayNumber.toString(),
                                    color = Color.Gray.copy(0.3f),
                                    fontSize = 14.sp
                                )
                            }
                        } else {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = onDismiss,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFE74C3C)),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    brush = androidx.compose.ui.graphics.SolidColor(Color(0xFFE74C3C))
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Cancel")
            }

            Button(
                onClick = {
                    val selectedCalendar = Calendar.getInstance()
                    selectedCalendar.set(selectedYear, selectedMonth, selectedDay)
                    onDateSelected(selectedCalendar.time)
                },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = Color(0xFFE91E63)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("OK", color = Color.White)
            }
        }
    }
}
