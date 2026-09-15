package com.mannanhosen.auraflowclock.presentation.add_edit_alarm.screen


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mannanhosen.auraflowclock.presentation.add_edit_alarm.componentes.QrScannerView

@Composable
fun QrDismissScreen(
    targetQrValue: String,
    onSolved: () -> Unit
) {
    var showError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF202224))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Scan the correct QR code to dismiss alarm",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(16.dp))

        QrScannerView(
            onQrDetected = { scanned ->
                if (scanned.trim() == targetQrValue.trim()) {
                    onSolved()
                } else {
                    showError = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )

        if (showError) {
            Text(
                "Wrong QR code, try again!",
                color = Color.Red,
                modifier = Modifier.padding(top = 12.dp)
            )
        }
    }
}
