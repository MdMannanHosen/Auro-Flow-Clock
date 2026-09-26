package com.mannanhosen.auraflowclock.presentation.bedtime.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mannanhosen.auraflowclock.ui.theme.AccentMint
import com.mannanhosen.auraflowclock.ui.theme.AppBackground
import com.mannanhosen.auraflowclock.ui.theme.CardBackground
import com.mannanhosen.auraflowclock.ui.theme.TextPrimary
import com.mannanhosen.auraflowclock.ui.theme.TextSecondary

private val fallAsleepOptions = listOf("10 min", "15 min", "20 min", "30 min")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AsleepScreen(
    initiallySelected: String = "15 min",
    onDismiss: () -> Unit,
    onDone: (String) -> Unit
) {

    var isOpen: Boolean by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(initiallySelected) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

//    if (isOpen) {
    ModalBottomSheet(
        onDismissRequest = {
            isOpen = false
            onDismiss()
        },
        sheetState = sheetState,
        containerColor = AppBackground,
        contentColor = TextPrimary,
        dragHandle = { BottomSheetDefaults.DragHandle(color = TextSecondary) },
        modifier = Modifier.padding(start = 3.dp, end = 3.dp, top = 27.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .height(700.dp)
                .background(AppBackground)
                .padding(bottom = 20.dp)
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

            Spacer(modifier = Modifier.height(36.dp))

            fallAsleepOptions.forEach { option ->
                val isSelected = option == selectedOption

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                        .clip(RoundedCornerShape(36.dp))
                        .background(CardBackground)
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

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(AppBackground),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Bedtime,
                                contentDescription = null,
                                tint = if (isSelected) AccentMint else TextSecondary
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Text(
                            text = option,
                            color = if (isSelected) AccentMint else TextPrimary,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Icon(
                        imageVector = if (isSelected) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = null,
                        tint = if (isSelected) AccentMint else TextSecondary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    isOpen = false
                    onDone(selectedOption)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp)
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
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewAsleepScreen() {
    AsleepScreen(
        initiallySelected = "00",
        onDismiss = {},
        onDone = {}
    )
}