package com.mannanhosen.auraflowclock.Presentation_Table.Navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mannanhosen.auraflowclock.Presentation_Table.NavigationBar.MainScreen
import com.mannanhosen.auraflowclock.Presentation_Table.alarm.AlarmSoundScreen
import com.mannanhosen.auraflowclock.Presentation_Table.alarm.CreateAlarmScreen
import com.mannanhosen.auraflowclock.Presentation_Table.alarm.VibrationScreen

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
            // এখানে MainScreen কল করুন এবং navController পাস করে দিন
            MainScreen(navController = navController)
        }

        composable(AppRoutes.AlarmSound.routes) {
            AlarmSoundScreen(navController = navController)
        }

        composable(AppRoutes.Vibration.routes) {
            VibrationScreen(navController = navController)
        }


        // AppNavGraph ফাইলে এই রুটটি যুক্ত করুন (যদি আগে থেকে না থাকে)
        composable("create_alarm") {
            CreateAlarmScreen(navController = navController)
        }


    }
}