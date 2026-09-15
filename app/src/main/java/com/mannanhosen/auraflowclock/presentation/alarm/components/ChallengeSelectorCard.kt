package com.mannanhosen.auraflowclock.presentation.alarm.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mannanhosen.auraflowclock.data.model.ChallengeType
@Composable
 fun ChallengeSelectorCard(
    selectedChallenge: ChallengeType,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF2C2E30))
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF3A3D40)),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = selectedChallenge.toIcon(),
                contentDescription = "Challenge",
                tint = Color(0xFF00E5A0),
                modifier = Modifier.size(22.dp)
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp)
        ) {
            Text(
                text = "Challenge Alarm",
                color = Color.White,
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp
            )
            Text(
                text = selectedChallenge.toLabel(),
                color = Color(0xFF9E9E9E),
                fontSize = 12.sp
            )
        }

        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = "Go to Challenge Method",
            tint = Color(0xFF9E9E9E)
        )
    }
}

// ✅ ChallengeType অনুযায়ী icon ম্যাপ করা
private fun ChallengeType.toIcon(): ImageVector = when (this) {
    ChallengeType.DEFAULT -> Icons.Default.EmojiEvents
    ChallengeType.MATH -> Icons.Default.Calculate
    ChallengeType.QR_CODE -> Icons.Default.QrCode
    ChallengeType.TAKE_PICTURE -> Icons.Default.CameraAlt
    ChallengeType.SHAKE -> Icons.Default.Vibration
}


private fun ChallengeType.toLabel(): String = when (this) {
    ChallengeType.DEFAULT -> "Default"
    ChallengeType.MATH -> "Math Filter"
    ChallengeType.QR_CODE -> "QR Code"
    ChallengeType.TAKE_PICTURE -> "Take a Picture"
    ChallengeType.SHAKE -> "Shake"
}
