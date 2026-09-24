 package com.mannanhosen.auraflowclock.presentation.bedtime.screen
 import androidx.compose.foundation.background
 import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness2
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.material.internal.TextScale

private val BackgroundDark = Color(0xFF202224)
private val AccentMint = Color(0xFF00BFA5)
private val CardSurface = Color(0xFF2A2D30)
private val TextPrimary = Color(0xFFF5F7F6)
private val TextSecondary = Color(0xFF8A8F94)


data class SleepCycleOption(
    val cycles: Int,
    val bedTime: String,
    val duration: String
)

private val sleepCycleOptions = listOf(
    SleepCycleOption(cycles = 6, bedTime = "10:00 PM", duration = "9h 00m"),
    SleepCycleOption(cycles = 5, bedTime = "11:30 PM", duration = "7h 30m"),
    SleepCycleOption(cycles = 4, bedTime = "1:00 AM", duration = "6h 00m")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SleepCycleScreen(
    initiallySelected: Int = 6,
    onDismiss: () -> Unit,
    onDone: (Int) -> Unit
) {

    var isOpen: Boolean by remember { mutableStateOf(false) }
    var selectedCycles by remember { mutableStateOf(initiallySelected) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Box(
        modifier = Modifier.fillMaxSize()
            .background(color = BackgroundDark),
        contentAlignment = Alignment.Center
    ) {

        OutlinedButton(
            onClick = { isOpen = true },
            colors = ButtonDefaults.buttonColors(
                containerColor = AccentMint,
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Open"
            )
        }
    }

    if (isOpen) {
        ModalBottomSheet(
            onDismissRequest = { isOpen = false; onDismiss() },
            sheetState = sheetState,
            containerColor = BackgroundDark,
            contentColor = TextPrimary,
            dragHandle = { BottomSheetDefaults.DragHandle(color = TextSecondary) },
            modifier = Modifier.padding(start = 3.dp, end = 3.dp, top = 27.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .height(700.dp)
                    .background(color = BackgroundDark)
                    .padding(bottom = 20.dp)
            ) {

                Text(
                    text = "Sleep cycle options",
                    color = TextPrimary,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "A good sleep cycle helps your body rest, recover, and wake up feeling refreshed.",
                    color = TextSecondary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(72.dp))

                sleepCycleOptions.forEach { option ->
                    val isSelected = option.cycles == selectedCycles

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp)
                            .clip(RoundedCornerShape(36.dp))
                            .background(CardSurface)
                            .then(
                                if (isSelected)
                                    Modifier.border(
                                        width = 1.5.dp,
                                        color = AccentMint,
                                        shape = RoundedCornerShape(36.dp)
                                    ) else Modifier
                            )
                            .clickable { selectedCycles = option.cycles }
                            .padding(horizontal = 16.dp, vertical = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(56.dp)
                                    .clip(CircleShape)
                                    .background(BackgroundDark),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Brightness2,
                                    contentDescription = null,
//                                    tint = TextSecondary
                                    tint = if(isSelected) AccentMint else TextSecondary
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column {
                                Text(
                                    text = "${option.cycles} cycles",
                                    color = if(isSelected) AccentMint else TextSecondary,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = option.bedTime,
                                    color = if(isSelected) AccentMint else TextSecondary,
                                    fontSize = 14.sp
                                )
                            }
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = option.duration,
                                color = if(isSelected) AccentMint else TextSecondary,
                                fontSize = 13.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Icon(
                                imageVector = if (isSelected) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                contentDescription = null,
                                tint = if (isSelected) AccentMint else TextSecondary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(72.dp))

                Button(
                    onClick = {
                        onDone(selectedCycles)
                        isOpen = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 20.dp)
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AccentMint,
                        contentColor = TextPrimary
                    )
                ) {
                    Text(
                        text = "Done",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewSleepCycleScreen() {
    SleepCycleScreen(
        initiallySelected = 6,
        onDismiss = {},
        onDone = {}
    )
}