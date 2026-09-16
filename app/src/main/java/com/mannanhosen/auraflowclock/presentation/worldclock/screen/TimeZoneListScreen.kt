package com.mannanhosen.auraflowclock.presentation.worldclock.screen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.mannanhosen.auraflowclock.presentation.worldclock.WorldClockViewModel
import com.mannanhosen.auraflowclock.presentation.worldclock.components.TimeZoneSelectionItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeZoneListScreen(
    onNavigateBack : () -> Unit,
    viewModel: WorldClockViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    val filteredTimeZones = if(searchQuery.isBlank()) {
        uiState.allTimeZones
    } else {
        uiState.allTimeZones.filter {
            it.displayName.contains(searchQuery, ignoreCase = true)
            it.timeZoneName.contains(searchQuery, ignoreCase = true)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Add Time Zone",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.headlineMedium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack){
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White

                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF202224),
                    titleContentColor = Color.White
                )
            )
        }
    ) {paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFF202224))
        ){
            OutlinedTextField(
                value = searchQuery,
                onValueChange = {searchQuery = it},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                label = {Text("Search cities and time zones")},
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF00E5A0),
                    focusedLabelColor = Color(0xFF00E5A0),
                    cursorColor = Color(0xFF00E5A0),
                    focusedTextColor = Color.White
                )
            )

            Box(
                modifier = Modifier.weight(1f)
            ){
                if(uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align (Alignment.Center)
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(4.dp, 4.dp, 4.dp, 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(filteredTimeZones) {  timeZone ->
                            TimeZoneSelectionItem(
                                timeZone = timeZone,
                                onSelectinChange = { isSelected ->
                                    viewModel.toggleTimeZoneSelection(timeZone)

                                }
                            )
                        }
                    }
                }
            }
        }

    }
}