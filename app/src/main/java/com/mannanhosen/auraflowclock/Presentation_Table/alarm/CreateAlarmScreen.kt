package com.mannanhosen.auraflowclock.Presentation_Table.alarm

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import kotlin.math.abs
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAlarmScreen(
    onCancel: () -> Unit = {},
    onSave: () -> Unit = {}
) {
    var alarmName by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf<Date?>(null) }
    var showCalendar by remember { mutableStateOf(false) }
    var selectedDayButton by remember { mutableStateOf<String?>(null) }

    // Switch states
    var alarmSoundEnabled by remember { mutableStateOf(false) }
    var vibrationEnabled by remember { mutableStateOf(false) }
    var snoozeEnabled by remember { mutableStateOf(false) }

    // Challenge states
    var selectedChallenge by remember { mutableStateOf<String?>(null) }
    var showChallengeDialog by remember { mutableStateOf(false) }

    val weekDays = listOf("M", "T", "W", "T", "F", "S", "S")
    val challengeOptions = listOf(
        "Math Puzzle",
        "Memory Game",
        "Typing Test",
        "Shake Phone",
        "Scan QR Code",
        "Solve Riddle"
    )

    val dateFormatter = remember { SimpleDateFormat("EEE, MMM d, yyyy", Locale.getDefault()) }
    val displayDate = selectedDate?.let { dateFormatter.format(it) } ?: "Select date"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF202224))
    ) {
        // Scrollable content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .verticalScroll(rememberScrollState())
               .padding(16.dp)
        ) {
            // Time Wheel Pickers
            val hours = remember { (1..12).map { it.toString() } }
            val minutes = remember { (0..59).map { it.toString().padStart(2, '0') } }
            val periods = remember { listOf("am", "pm") }

            val startIndex = 1000
            val hourState = rememberPagerState(
                pageCount = { Int.MAX_VALUE },
                initialPage = startIndex * hours.size + 10
            )
            val minuteState = rememberPagerState(
                pageCount = { Int.MAX_VALUE },
                initialPage = startIndex * minutes.size + 0
            )
            val periodState = rememberPagerState(pageCount = { periods.size })

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                   .padding(vertical = 16.dp),
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
                    style = TextStyle(fontSize = 50.sp, fontWeight = FontWeight.Bold, color = Color(0xFFDDDAD5)),
                    modifier = Modifier.padding(horizontal = 4.dp)
                )

                TimeWheelPicker(
                    items = minutes,
                    state = minuteState,
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

            Spacer(modifier = Modifier.height(16.dp))

            // Day selection row
            Card(

                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF2A2C30)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = if (selectedDayButton == null) "Every day" else "Every ${selectedDayButton}",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        weekDays.forEach { day ->
                            val isSelected = selectedDayButton == day
                            Surface(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .clickable {
                                        selectedDayButton = if (isSelected) null else day
                                    },
                                color = if (isSelected) Color(0xFFE91E63) else Color(0xFF3A3C40),
                                contentColor = Color.White
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = day,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                        fontSize = 18.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Date Selector with Calendar Icon
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF2A2C30)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showCalendar = true }
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CalendarToday,
                            contentDescription = "Select date",
                            tint = Color(0xFFE91E63),
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            text = displayDate,
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        tint = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Alarm Name
            OutlinedTextField(
                value = alarmName,
                onValueChange = { alarmName = it },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = Color.White, fontSize = 16.sp),
                placeholder = { Text("Alarm name", color = Color.Gray) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFE91E63),
                    unfocusedBorderColor = Color(0xFF3A3C40),
                    focusedLabelColor = Color(0xFFE91E63),
                    cursorColor = Color(0xFFE91E63)
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Alarm Sound Switch (Same as Vibration)
            SettingsSwitch(
                title = "Alarm sound",
                checked = alarmSoundEnabled,
                onCheckedChange = { alarmSoundEnabled = it }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Vibration Switch
            SettingsSwitch(
                title = "Vibration",
                checked = vibrationEnabled,
                onCheckedChange = { vibrationEnabled = it }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Snooze Switch (Same as Vibration)
            SettingsSwitch(
                title = "Snooze",
                checked = snoozeEnabled,
                onCheckedChange = { snoozeEnabled = it }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Take a Challenge Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF2A2C30)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = Color(0xFFFFC107),
                                modifier = Modifier.size(24.dp)
                            )
                            Text(
                                text = "Take a Challenge",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        if (selectedChallenge != null) {
                            Surface(
                                modifier = Modifier.clickable { selectedChallenge = null },
                                color = Color(0xFFE91E63).copy(alpha = 0.2f),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        text = "Clear",
                                        color = Color(0xFFE91E63),
                                        fontSize = 12.sp
                                    )
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Clear",
                                        tint = Color(0xFFE91E63),
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Challenge selection button
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showChallengeDialog = true },
                        colors = CardDefaults.cardColors(
                            containerColor = if (selectedChallenge != null) Color(0xFFE91E63).copy(alpha = 0.15f) else Color(0xFF3A3C40)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Games,
                                    contentDescription = null,
                                    tint = if (selectedChallenge != null) Color(0xFFE91E63) else Color(0xFF9E9E9E),
                                    modifier = Modifier.size(24.dp)
                                )
                                Text(
                                    text = selectedChallenge ?: "Select a challenge",
                                    color = if (selectedChallenge != null) Color(0xFFE91E63) else Color(0xFF9E9E9E),
                                    fontSize = 14.sp
                                )
                            }
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = null,
                                tint = Color.Gray
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Challenge description
                    if (selectedChallenge != null) {
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            color = Color(0xFF1A1C1E),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Info,
                                    contentDescription = null,
                                    tint = Color(0xFF4CAF50),
                                    modifier = Modifier.size(20.dp)
                                )
                                Text(
                                    text = "When alarm rings, you must complete this challenge to stop it",
                                    color = Color.White.copy(alpha = 0.7f),
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        // Cancel and Save Buttons at bottom
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = onCancel,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color(0xFFE74C3C)
                ),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    brush = androidx.compose.ui.graphics.SolidColor(Color(0xFFE74C3C))
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Cancel", fontSize = 16.sp, fontWeight = FontWeight.Medium)
            }

            Button(
                onClick = onSave,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE91E63),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Save", fontSize = 16.sp, fontWeight = FontWeight.Medium)
            }
        }
    }

    // Real Calendar Dialog
    if (showCalendar) {
        RealCalendarDialog(
            onDateSelected = { date ->
                selectedDate = date
                selectedDayButton = null
                showCalendar = false
            },
            onDismiss = { showCalendar = false }
        )
    }

    // Challenge Selection Dialog
    if (showChallengeDialog) {
        ChallengeSelectionDialog(
            options = challengeOptions,
            currentSelection = selectedChallenge,
            onSelect = { challenge ->
                selectedChallenge = challenge
                showChallengeDialog = false
            },
            onDismiss = { showChallengeDialog = false }
        )
    }
}

@Composable
fun ChallengeSelectionDialog(
    options: List<String>,
    currentSelection: String?,
    onSelect: (String) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF2A2C30))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Select Challenge",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.Gray)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Choose a challenge to complete when alarm rings",
                    color = Color.Gray,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                options.forEach { option ->
                    val isSelected = currentSelection == option
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onSelect(option) }
                            .padding(vertical = 4.dp),
                        color = if (isSelected) Color(0xFFE91E63).copy(alpha = 0.15f) else Color.Transparent,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                when {
                                    option.contains("Math") -> Icon(Icons.Default.Calculate, contentDescription = null, tint = Color(0xFF4CAF50), modifier = Modifier.size(24.dp))
                                    option.contains("Memory") -> Icon(Icons.Default.Psychology, contentDescription = null, tint = Color(0xFF2196F3), modifier = Modifier.size(24.dp))
                                    option.contains("Typing") -> Icon(Icons.Default.Keyboard, contentDescription = null, tint = Color(0xFFFF9800), modifier = Modifier.size(24.dp))
                                    option.contains("Shake") -> Icon(Icons.Default.CrisisAlert, contentDescription = null, tint = Color(0xFF9C27B0), modifier = Modifier.size(24.dp))
                                    option.contains("Scan") -> Icon(Icons.Default.QrCodeScanner, contentDescription = null, tint = Color(0xFF00BCD4), modifier = Modifier.size(24.dp))
                                    else -> Icon(Icons.Default.Lightbulb, contentDescription = null, tint = Color(0xFFFFC107), modifier = Modifier.size(24.dp))
                                }
                                Text(
                                    text = option,
                                    color = if (isSelected) Color(0xFFE91E63) else Color.White,
                                    fontSize = 16.sp
                                )
                            }
                            if (isSelected) {
                                Icon(Icons.Default.Check, contentDescription = null, tint = Color(0xFFE91E63))
                            }
                        }
                    }
                    if (option != options.last()) {
                        Divider(color = Color(0xFF3A3C40))
                    }
                }
            }
        }
    }
}

@Composable
fun RealCalendarDialog(
    onDateSelected: (Date) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth()
                .padding(5.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF2A2C30))
        ) {
            RealCalendarView(
                onDateSelected = onDateSelected,
                onDismiss = onDismiss
            )
        }
    }
}

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

    val monthNames = listOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    )
    val dayNames = listOf("S", "M", "T", "W", "T", "F", "S")

    val tempCal = Calendar.getInstance().apply {
        set(Calendar.YEAR, currentYear)
        set(Calendar.MONTH, currentMonth)
        set(Calendar.DAY_OF_MONTH, 1)
    }
    val daysInMonth = tempCal.getActualMaximum(Calendar.DAY_OF_MONTH)
    val firstDayOfWeek = tempCal.get(Calendar.DAY_OF_WEEK) - 1

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
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

            Text(
                text = "${monthNames[currentMonth]} $currentYear",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

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

        Spacer(modifier = Modifier.height(12.dp))

        Column {
            for (row in 0 until 6) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    for (col in 0 until 7) {
                        val dayNumber = (row * 7) + col - firstDayOfWeek + 1
                        val isValidDay = dayNumber in 1..daysInMonth

                        val checkCalendar = Calendar.getInstance().apply {
                            set(currentYear, currentMonth, dayNumber)
                        }
                        val isFutureOrToday = !checkCalendar.time.before(todayDate)

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
                                            isToday -> Color(0xFFE91E63).copy(alpha = 0.3f)
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
                                        else -> Color.White.copy(alpha = 0.7f)
                                    },
                                    fontWeight = if (isSelected || isToday) FontWeight.Bold else FontWeight.Normal,
                                    fontSize = 14.sp
                                )
                            }
                        } else if (isValidDay) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1f)
                                    .padding(2.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = dayNumber.toString(),
                                    color = Color.Gray.copy(alpha = 0.3f),
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
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Gray),
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
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE91E63)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("OK", color = Color.White)
            }
        }
    }
}

@Composable
fun SettingsSwitch(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2A2C30)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color(0xFFE91E63),
                    checkedTrackColor = Color(0xFFE91E63).copy(alpha = 0.5f),
                    uncheckedThumbColor = Color.Gray,
                    uncheckedTrackColor = Color(0xFF3A3C40)
                )
            )
        }
    }
}

@SuppressLint("FrequentlyChangingValue")
@Composable
fun TimeWheelPicker(
    items: List<String>,
    state: PagerState,
    modifier: Modifier = Modifier,
    isInfinite: Boolean = false
) {
    val flingBehavior = PagerDefaults.flingBehavior(
        state = state,
        pagerSnapDistance = PagerSnapDistance.atMost(50),
        snapPositionalThreshold = 0.01f
    )

    VerticalPager(
        state = state,
        modifier = modifier.height(250.dp),
        contentPadding = PaddingValues(vertical = 80.dp),
        flingBehavior = flingBehavior,
        beyondViewportPageCount = 1
    ) { page ->
        val itemIndex = if (isInfinite) page % items.size else page

        val pageOffset = remember(state, page) {
            derivedStateOf {
                val offset = (state.currentPage - page) + state.currentPageOffsetFraction
                offset.coerceIn(-1f, 1f)
            }
        }.value

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = items[itemIndex],
                fontWeight = FontWeight.W900,
                style = TextStyle(
                    fontSize = 48.sp,
                    color = if (abs(pageOffset) < 0.2f) Color(0xFFDDDAD5) else Color(0x99C9C7C3),
                    fontWeight = if (abs(pageOffset) < 0.2f) FontWeight.ExtraBold else FontWeight.Medium
                )
            )
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4_XL)
@Composable
fun PreviewCreateAlarmScreen() {
    CreateAlarmScreen()
}