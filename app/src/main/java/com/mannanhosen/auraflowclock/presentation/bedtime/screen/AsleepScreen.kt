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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
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

private val BackgroundDark = Color(0xFF202224)
private val AccentMint = Color(0xFF00BFA5)
private val CardSurface = Color(0xFF2A2D30)
private val TextPrimary = Color(0xFFDDDAD5)
private val TextSecondary = Color(0xFF9CA3AF)
private val fallAsleepOptions = listOf("10 min", "15 min", "20 min", "30 min")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AsleepScreen(
    initiallySelected: String = "15 min",
    onDismiss: () -> Unit,
    onDone: (String) -> Unit
) {

    var isOpen by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(initiallySelected) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Box(
        modifier = Modifier
            .background(BackgroundDark)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        OutlinedButton(
            onClick = { isOpen = true },
            colors = ButtonDefaults.buttonColors(
                containerColor = AccentMint
            )
        ) {
            Text("Open")
        }
    }

    if (isOpen) {
        ModalBottomSheet(
            onDismissRequest = {
                isOpen = false
                onDismiss()
            },
            sheetState = sheetState,
            containerColor = BackgroundDark,
            contentColor = TextPrimary,
            dragHandle = { BottomSheetDefaults.DragHandle(color = TextSecondary) },
            modifier = Modifier.padding(start = 3.dp, end = 3.dp, top = 27.dp)

        ) {
            Column(
                modifier = Modifier
                    .height(700.dp)
                    .background(BackgroundDark)
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 24.dp)
            ) {
                Text(
                    text = "Time to fall asleep",
                    color = TextPrimary,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Choose how long it takes you to fall asleep before your bedtime.",
                    color = TextSecondary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(72.dp))

                fallAsleepOptions.forEach { option ->
                    val isSelected = false

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp)
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
                            .clickable { selectedOption = option }
                            .padding(horizontal = 16.dp, vertical = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = option,
                            color = TextPrimary,
                            fontSize = 16.sp
                        )

                        RadioButton(
                            selected = isSelected,
                            onClick = { selectedOption = option },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = AccentMint,
                                unselectedColor = TextSecondary
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(72.dp))

                Button(
                    onClick = {
                        isOpen = false
                        onDone(selectedOption)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp)
                        .height(56.dp),
                    shape = RoundedCornerShape(36.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AccentMint,
                        contentColor = TextPrimary
                    )
                ) {
                    Text(
                        text = "Done",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,

                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewAsleepScreen() {
    AsleepScreen(
        initiallySelected = "15 min",
        onDismiss = {},
        onDone = {}
    )

}