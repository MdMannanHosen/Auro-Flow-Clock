package com.mannanhosen.auraflowclock.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mannanhosen.auraflowclock.presentation.add_edit_alarm.add_alarm_viewmodel.AddEditAlarmViewModel
import com.mannanhosen.auraflowclock.presentation.add_edit_alarm.componentes.AlarmSettingsCard
import com.mannanhosen.auraflowclock.presentation.alarm.components.AlarmTimePicker
import com.mannanhosen.auraflowclock.presentation.alarm.components.EveryDay
import com.mannanhosen.auraflowclock.presentation.alarm.components.RealCalenderDialog
import java.util.Date

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val Pink500 = Color(0xFFE91E63)
val Onyx = Color(0xFF3A3C40)
val LightGray = Color(0xFF202224)
val Gray = Color(0xFFDDDAD5)
val SeaGreen = Color(0x99C9C7C3)
val Caviar = Color(0xFF2A2C30)

val BlackPrimary = Color(0xFF000000)
val WhitePrimary = Color(0xFFFFFFFF)
val DarkGray = Color(0xFF1A1A1A)
//val LightGray = Color(0xFFF5F5F5)

// Accent colors - Red and Orange
val RedAccent = Color(0xFFE53E3E)
val OrangeAccent = Color(0xFFFF6B35)
val DarkRed = Color(0xFFCC2E2E)
val DarkOrange = Color(0xFFE55A2B)

// Light Theme Colors
val WorldClockPrimary = BlackPrimary
val WorldClockPrimaryVariant = DarkGray
val WorldClockSecondary = RedAccent
val WorldClockSecondaryVariant = DarkRed
val WorldClockBackground = WhitePrimary
val WorldClockSurface = LightGray
val WorldClockError = RedAccent
val WorldClockOnPrimary = WhitePrimary
val WorldClockOnSecondary = WhitePrimary
val WorldClockOnBackground = BlackPrimary
val WorldClockOnSurface = BlackPrimary
val WorldClockOnError = WhitePrimary
val WorldClockAccent = OrangeAccent

// Dark Theme Colors
val WorldClockDarkPrimary = WhitePrimary
val WorldClockDarkPrimaryVariant = LightGray
val WorldClockDarkSecondary = OrangeAccent
val WorldClockDarkBackground = DarkGray
val WorldClockDarkSurface = Color(0xFF2A2A2A)
val WorldClockDarkOnPrimary = BlackPrimary
val WorldClockDarkOnSecondary = BlackPrimary
val WorldClockDarkOnBackground = WhitePrimary
val WorldClockDarkOnSurface = WhitePrimary





