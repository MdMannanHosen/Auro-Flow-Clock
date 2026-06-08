package com.mannanhosen.auraflowclock.Presentation_Table.alarm

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun VibrationScreen(navController: NavController? = null) {

}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, device = Devices.PIXEL_4_XL)
@Composable
fun VibrationScreenPreview() {
    val dummyNavController = rememberNavController()
    VibrationScreen(navController = dummyNavController)
}