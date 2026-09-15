package com.mannanhosen.auraflowclock.presentation.add_edit_alarm.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mannanhosen.auraflowclock.presentation.add_edit_alarm.add_alarm_viewmodel.QrScanViewModel
import com.mannanhosen.auraflowclock.presentation.add_edit_alarm.componentes.QrScannerView

@Composable
fun QrSetupScreen(
    navController: NavController,
    viewModel: QrScanViewModel = hiltViewModel()
) {

    val scannedValue by viewModel.scannedValue.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF202224))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Scan the QR code you want to use\nto dismiss this alarm",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(24.dp))

        QrScannerView(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            onQrDetected = { value ->
                viewModel.onQrScanned(value)
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        scannedValue?.let { value ->

            Text(
                text = "Detected: $value",
                color = Color(0xFF00E5A0)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    Log.d("QR_TEST", "Use QR Button Clicked")
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("qr_target_value", value)

                    navController.popBackStack()
                }
            ) {
                Text("Use This QR Code")
            }
        }
    }
}