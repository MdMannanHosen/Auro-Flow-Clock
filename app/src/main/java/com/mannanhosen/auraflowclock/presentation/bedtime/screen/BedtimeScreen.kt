package com.mannanhosen.auraflowclock.presentation.bedtime.screen
import android.net.http.SslCertificate.saveState
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.util.TimeUtils
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.mannanhosen.auraflowclock.data.model.entity.BedTime
import com.mannanhosen.auraflowclock.presentation.bedtime.components.BedTimeMode
import com.mannanhosen.auraflowclock.presentation.bedtime.components.RecommendationCard
import com.mannanhosen.auraflowclock.presentation.bedtime.components.TimePickerButton
import com.mannanhosen.auraflowclock.presentation.bedtime.viewmodel.BedTimeViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BedtimeScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel:  BedTimeViewModel = hiltViewModel()
 ) {

    val uiState by viewModel.uiState.collectAsState()
    val isWakeUpMode = uiState.mode == BedTimeMode.WAKE_UP_TIME

    val promptMessage =
        if (isWakeUpMode) {
            Text("The time to go")
        } else {
            Text("The time to go")
        }


    val recommendationsTitle =
        if (isWakeUpMode) {
            Text("Time to bad")
        } else {
            Text("Time to bad")
        }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            FilterChip(
                selected = isWakeUpMode,
                onClick = { viewModel.onModeChange(BedTimeMode.WAKE_UP_TIME) },
                label = { Text("This is text") }
            )
            Spacer(Modifier.width(8.dp))
            FilterChip(
                selected = !isWakeUpMode,
                onClick = { viewModel.onModeChange(BedTimeMode.BED_TIME) },
                label = { Text("This is text") }
            )
        }

    }
}