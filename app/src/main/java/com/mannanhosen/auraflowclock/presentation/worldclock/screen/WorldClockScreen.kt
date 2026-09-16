package com.mannanhosen.auraflowclock.presentation.worldclock.screen
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mannanhosen.auraflowclock.presentation.worldclock.WorldClockViewModel
import com.mannanhosen.auraflowclock.presentation.worldclock.components.TimeZoneCard

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorldClockScreen(
    onNavigateToTimeZoneList: () -> Unit,
    viewModel: WorldClockViewModel = hiltViewModel(),
    onNavigateBack: () -> Boolean
) {
    val uiState by viewModel.uiState.collectAsState()
    val currentTimes by viewModel.currentTimes.collectAsState()

    LaunchedEffect(uiState.error) {
        uiState.error?.let {
            // Handle error display
            viewModel.clearError()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "World Clock",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.headlineMedium
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF202224),
                    titleContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToTimeZoneList,
                containerColor = Color(0xFF00E5A0), // Orange accent
                contentColor = Color.White,
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Time Zone",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .background(Color(0xFF202224))
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                currentTimes.isEmpty() -> {
                    EmptyState(
                        modifier = Modifier.align(Alignment.Center),
                        onAddTimeZone = onNavigateToTimeZoneList
                    )
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(20.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(currentTimes) { timeInfo ->
                            TimeZoneCard(
                                timeInfo = timeInfo,
                                onRemove = {
                                    viewModel.toggleTimeZoneSelection(timeInfo.timeZone)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyState(
    modifier: Modifier = Modifier,
    onAddTimeZone: () -> Unit
) {
    Column(
        modifier = modifier.padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "No time zones added yet",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Add your first time zone to get started",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = onAddTimeZone,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF00E5A0)
            )
        ) {
            Text("Add Time Zone")
        }
    }
}
