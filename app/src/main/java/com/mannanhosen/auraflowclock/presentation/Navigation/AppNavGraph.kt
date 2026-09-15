package com.mannanhosen.auraflowclock.presentation.Navigation
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mannanhosen.auraflowclock.presentation.NavigationBar.MainScreen
import com.mannanhosen.auraflowclock.presentation.add_edit_alarm.screen.AddEditAlarmScreen
import com.mannanhosen.auraflowclock.presentation.add_edit_alarm.screen.AlarmSoundScreen
import com.mannanhosen.auraflowclock.presentation.add_edit_alarm.screen.MathChallengeScreen
import com.mannanhosen.auraflowclock.presentation.add_edit_alarm.screen.QrDismissScreen
import com.mannanhosen.auraflowclock.presentation.add_edit_alarm.screen.QrSetupScreen
import com.mannanhosen.auraflowclock.presentation.add_edit_alarm.screen.RingtonePickerScreen
import com.mannanhosen.auraflowclock.presentation.add_edit_alarm.screen.ShakeChallengeScreen
import com.mannanhosen.auraflowclock.presentation.add_edit_alarm.screen.SnoozeScreen
import com.mannanhosen.auraflowclock.presentation.add_edit_alarm.componentes.VibrationScreen
import com.mannanhosen.auraflowclock.presentation.box_breathing.BoxBreathingScreen
import com.mannanhosen.auraflowclock.presentation.challenge.ChallengeMethodScreen
import com.mannanhosen.auraflowclock.presentation.worldclock.screen.TimeZoneListScreen
import com.mannanhosen.auraflowclock.presentation.worldclock.screen.WorldClockScreen

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = AppRoutes.Home.routes
    ) {
        composable(AppRoutes.Home.routes) {
            MainScreen(navController = navController)
        }

        composable(AppRoutes.AlarmSound.routes) {
            AlarmSoundScreen(navController = navController)
        }

        composable(AppRoutes.Vibration.routes) {
            VibrationScreen(navController = navController)
        }

        composable("create_alarm") {
            AddEditAlarmScreen(navController = navController,
               onSave = { navController.popBackStack() },
                onCancel = { navController.popBackStack() }

            )
        }



        composable(
            route = "challenge_method_screen?alarmId={alarmId}",
            arguments = listOf(
                navArgument("alarmId") {
                    type = NavType.IntType
                    defaultValue = -1   // নতুন alarm এর জন্য -1 মানে "এখনো id নেই"
                }
            )
        ) {
            ChallengeMethodScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToQrSetup = {  navController.navigate("qr_setup_screen") },
                onNavigateToMathSetup = {navController.navigate("math_challenge_dismiss")},
                onNavigateToShakeChallenge = {navController.navigate("shake_challenge_dismiss")}
            )
        }

        composable("qr_setup_screen") {
            QrSetupScreen(navController = navController)
        }

        composable("math_challenge_dismiss") {
            MathChallengeScreen(onSolved = { navController.popBackStack() })
        }

        composable("shake_challenge_dismiss") {
            ShakeChallengeScreen(onSolved = { /* alarm service কে জানাও */ })
        }



        composable(
            route = "qr_challenge_dismiss/{qrTarget}",
            arguments = listOf(navArgument("qrTarget") { type = NavType.StringType })
        ) { backStackEntry ->
            val qrTarget = backStackEntry.arguments?.getString("qrTarget") ?: ""
            QrDismissScreen(targetQrValue = qrTarget, onSolved = { /* alarm service কে জানাও */ })
        }

        composable(
            route = "ringtone_picker_screen?currentUri={currentUri}",
            arguments = listOf(
                navArgument("currentUri") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) { backStackEntry ->
            val currentUri = backStackEntry.arguments?.getString("currentUri")

            RingtonePickerScreen(
                currentUri = currentUri,
                onNavigateBack = { navController.popBackStack() },
                onRingtoneSelected = { title, uriString ->
                    // ✅ ফলাফল আগের স্ক্রিনে পাঠানো হচ্ছে
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("selected_ringtone_title", title)
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("selected_ringtone_uri", uriString)
                    navController.popBackStack()
                }
            )
        }


        composable(
            route = "vibration_screen?currentPattern={currentPattern}",
            arguments = listOf(
                navArgument("currentPattern") {
                    type = NavType.StringType
                    defaultValue = "Short Pulse"
                }
            )
        ) { backStackEntry ->
            val currentPattern = backStackEntry.arguments?.getString("currentPattern") ?: "Short Pulse"
            VibrationScreen(navController = navController, currentPattern = currentPattern)
        }

        composable(
            route = "snooze_screen?duration={duration}&count={count}",
            arguments = listOf(
                navArgument("duration") { type = NavType.IntType; defaultValue = 5 },
                navArgument("count") { type = NavType.IntType; defaultValue = 3 }
            )
        ) { backStackEntry ->
            val duration = backStackEntry.arguments?.getInt("duration") ?: 5
            val count = backStackEntry.arguments?.getInt("count") ?: 3
            SnoozeScreen(navController = navController, currentDuration = duration, currentCount = count)
        }

        composable("box_breathing_screen") {
            BoxBreathingScreen()
        }

        composable("WorldClockScreen") {
            WorldClockScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToTimeZoneList = {
                    navController.navigate("timezone_list")
                },
                viewModel = hiltViewModel()
            )
        }


        composable("timezone_list") {
            TimeZoneListScreen(
                onNavigateBack = {
                   navController.popBackStack()
                }
            )
        }
    }
}