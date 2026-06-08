package com.mannanhosen.auraflowclock.Presentation_Table

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.mannanhosen.auraflowclock.Presentation_Table.Navigation.AppNavGraph
import com.mannanhosen.auraflowclock.ui.theme.AuraFlowClockTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AuraFlowClockTheme {
                // ১. মেইন নেভিগেশন কন্ট্রোলার তৈরি করা হলো
                val navController = rememberNavController()

                // ২. সরাসরি অ্যাপের নেভিগেশন গ্রাফটি রান করা হলো
                AppNavGraph(navController = navController)
            }
        }
    }
}