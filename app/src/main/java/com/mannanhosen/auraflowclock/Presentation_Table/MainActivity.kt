package com.mannanhosen.auraflowclock.Presentation_Table
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.mannanhosen.auraflowclock.Presentation_Table.NavigationBar.MainScreen
import com.mannanhosen.auraflowclock.ui.theme.AuraFlowClockTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AuraFlowClockTheme {


                      MainScreen(
                          modifier = Modifier .background(Color(0xFF181A1B))
                      )

            }
        }
    }
}

