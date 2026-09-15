package com.mannanhosen.auraflowclock.presentation.bedtime.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TimePickerButton(
   timeText : String,
    onClick : () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
      onClick = onClick,
        modifier = Modifier
            .height(48.dp)
            .widthIn(200.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        ), shape = MaterialTheme.shapes.small
    ) {
     Row(
       verticalAlignment = Alignment.CenterVertically,
         horizontalArrangement = Arrangement.SpaceBetween
     ) {
         Text(
           text = timeText,
            style = MaterialTheme.typography.titleLarge,
             )
         Spacer(Modifier.width(8.dp))

         Icon(
            imageVector = Icons.Default.ChevronRight,
             contentDescription = "select time"
         )
     }
    }
}

@Preview
@Composable
fun TimePickerButtonPreview() {
    TimePickerButton(
        timeText = "08:00 AM",
        onClick = {}
    )
}