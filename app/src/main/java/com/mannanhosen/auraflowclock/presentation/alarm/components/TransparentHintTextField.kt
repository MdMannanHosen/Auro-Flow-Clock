package com.mannanhosen.auraflowclock.presentation.alarm.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Composable
fun TransparentHintTextField(
    text: String,
    hint: String,
    modifier: Modifier = Modifier,
    isHintVisible: Boolean = true,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle = TextStyle(),
    singleLine: Boolean = true, // 'L' বড় হাতের করা হয়েছে
    onFocusChange: (FocusState) -> Unit
) {
    Box(modifier = modifier) {
        OutlinedTextField(
            value = text,
            colors = OutlinedTextFieldDefaults.colors(
                 focusedBorderColor = Color(0xFFE91E63),
                 unfocusedBorderColor = Color(0xFF3A3C40),
                 focusedLabelColor = Color(0xFFE91E63),
                 cursorColor = Color(0xFFE91E63),
                focusedTextColor = Color.White,       // <-- এটা যোগ করুন
                unfocusedTextColor = Color.White
            ),

            onValueChange = onValueChange,
            singleLine = singleLine, // প্যারামিটার নাম ঠিক করা হয়েছে
            textStyle = textStyle,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    onFocusChange(it)
                }
        )

        if (isHintVisible) {
            Text(text = hint, style= textStyle, color = Color.White)

        }

    }
}