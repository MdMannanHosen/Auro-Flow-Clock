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
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mannanhosen.auraflowclock.Presentation_Table.Navigation.AppRoutes
import kotlin.math.abs
import java.util.Calendar
import java.util.Date

@SuppressLint("RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAlarmScreen(
    onCancel: () -> Unit = {},
    onSave: () -> Unit = {},
    navController: NavController? = null // প্রিভিউ এরর ফিক্স করার জন্য অপশনাল করা হলো
) {
    var selectedDay by remember { mutableStateOf<Date?>(null) }
    var alarmName by remember { mutableStateOf("") }

    // প্রতিটির জন্য আলাদা আলাদা স্টেট ডিফাইন করা হলো
    var alarmSoundEnabled by remember { mutableStateOf(false) }
    var vibrationEnabled by remember { mutableStateOf(false) }
    var snoozeEnabled by remember { mutableStateOf(false) }

    var showCalender by remember { mutableStateOf(false) }
    var selectedCalenderButton by remember { mutableStateOf<String?>(null) }
    val weeDays = listOf("S", "M", "T", "W", "T", "F", "S", "s")

    val hours = remember { (1..12).map { it.toString() } }
    val minutes = remember { (0..59).map { it.toString().padStart(2, '0') } }
    val periods = remember { listOf("am", "pm") }

    val startIndex = 1000
    val hourState = rememberPagerState(pageCount = { Int.MAX_VALUE }, initialPage = startIndex * hours.size + 10)
    val minutesState = rememberPagerState(pageCount = { Int.MAX_VALUE }, initialPage = startIndex * minutes.size + 0)
    val periodState = rememberPagerState(pageCount = { periods.size })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF202224))
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            TimeWheelPicker(items = hours, state = hourState, modifier = Modifier.weight(1f), isInfinite = true)
            Text(text = ":", style = TextStyle(fontSize = 50.sp, fontWeight = FontWeight.Bold, color = Color(0xFFDDDAD5)))
            Spacer(modifier = Modifier.width(12.dp))
            TimeWheelPicker(items = minutes, state = minutesState, modifier = Modifier.weight(1f), isInfinite = true)
            Spacer(modifier = Modifier.width(12.dp))
            TimeWheelPicker(items = periods, state = periodState, modifier = Modifier.weight(0.8f), isInfinite = false)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF2A2C30))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = if (selectedCalenderButton == null) "Every day" else "Every $selectedCalenderButton",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    weeDays.forEach { day ->
                        val isSelected = selectedCalenderButton == day
                        Surface(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .clickable {
                                    selectedCalenderButton = if (isSelected) null else day
                                },
                            color = if (isSelected) Color(0xFFE91E63) else Color(0xFF3A3C40)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = day,
                                    color = Color.White,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF2A2C30))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showCalender = true }
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        contentDescription = "Selected Date",
                        imageVector = Icons.Default.CalendarToday,
                        tint = Color(0xFFE91E63)
                    )
                    Text(
                        text = "Selected Days",
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

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Alarm Name", color = Color.Gray)},
            value = alarmName,
            onValueChange = { alarmName = it },
            textStyle = TextStyle(color = Color.White, fontSize = 16.sp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFE91E63),
                unfocusedBorderColor = Color(0xFF3A3C40),
                focusedLabelColor = Color(0xFFE91E63),
                cursorColor = Color(0xFFE91E63)
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        // ১. Alarm Sound - নেভিগেশন অ্যাকশনসহ পাস করা হলো
        // CreateAlarmScreen এর ভেতর 'Alarm Sound' সুইচটি এভাবে লিখুন:
        SettingsSwitch(
            title = "Alarm Sound",
            checked = alarmSoundEnabled,
            onCheckedChange = { alarmSoundEnabled = it },
            onClick = {
                // সেফটি চেক: কন্ট্রোলার এবং তার কারেন্ট ডেস্টিনেশন নাল না হলে নেভিগেট করবে
                if (navController != null && navController.currentDestination != null) {
                    try {
                        navController.navigate(AppRoutes.AlarmSound.routes)
                    } catch (e: Exception) {
                        // কোনো কারণে গ্রাফের বাইরে হলে ক্র্যাশ না করে এরর লগ করবে
                        android.util.Log.e("NavigationError", "Failed to navigate: ${e.message}")
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // ২. Vibration - সঠিক স্টেট সেট করা হলো
        SettingsSwitch(
            title = "Vibration",
            checked = vibrationEnabled,
            onCheckedChange = { vibrationEnabled = it },
            onClick = {

                if (navController != null && navController.currentDestination != null) {
                    try {
                        navController.navigate(AppRoutes.Vibration.routes)
                    } catch (e: Exception) {
                        // কোনো কারণে গ্রাফের বাইরে হলে ক্র্যাশ না করে এরর লগ করবে
                        android.util.Log.e("NavigationError", "Failed to navigate: ${e.message}")
                    }
                }

            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // ৩. Snooze - সঠিক স্টেট সেট করা হলো
        SettingsSwitch(
            title = "Snooze",
            checked = snoozeEnabled,
            onCheckedChange = { snoozeEnabled = it },
            onClick = {
                if (navController != null && navController.currentDestination != null) {
                    try {
                        navController.navigate(AppRoutes.Vibration.routes)
                    } catch (e: Exception) {
                        // কোনো কারণে গ্রাফের বাইরে হলে ক্র্যাশ না করে এরর লগ করবে
                        android.util.Log.e("NavigationError", "Failed to navigate: ${e.message}")
                    }
                }
            }
        )
    }

    if (showCalender) {
        RealCalenderDialog(
            onDateSelected = { date ->
                selectedDay = date
                selectedCalenderButton = null
                showCalender = false
            },
            onDismiss = {
                showCalender = false
            }
        )
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
                style = TextStyle(
                    fontSize = 48.sp,
                    color = if (abs(pageOffset) < 0.2f) Color(0xFFDDDAD5) else Color(0x99C9C7C3),
                    fontWeight = if (abs(pageOffset) < 0.2f) FontWeight.ExtraBold else FontWeight.Medium
                )
            )
        }
    }
}

@Composable
fun RealCalenderDialog(
    onDateSelected: (Date) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
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

// SettingsSwitch কে মডিফাই করা হলো যাতে ক্লিক অ্যাকশন (নেভিগেশন) হ্যান্ডেল করতে পারে
@Composable
fun SettingsSwitch(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onClick: (() -> Unit)? = null // ক্লিক ইভেন্টের জন্য অপশনাল ল্যাম্বডা
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2A2C30))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
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
                    checkedIconColor = Color(0xFFE91E63),
                  checkedTrackColor = Color(0xFFE91E63) ,//.copy(alpha = 0.5f),
                    uncheckedThumbColor = Color.Gray,
                    uncheckedTrackColor = Color(0xFF3A3C40)
                )
            )
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4_XL)
@Composable
fun PreviewCreateAlarmScreen() {
    val dummyNavController = rememberNavController()
    CreateAlarmScreen(navController = dummyNavController) // এখন আর এরর দেখাবে না
}