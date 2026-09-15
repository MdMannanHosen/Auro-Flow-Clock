package com.mannanhosen.auraflowclock.presentation.challenge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mannanhosen.auraflowclock.data.model.ChallengeType
import com.mannanhosen.auraflowclock.presentation.add_edit_alarm.add_alarm_viewmodel.ChallengeMethodViewModel

data class ChallengeOption(
    val type: ChallengeType,
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val color: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChallengeMethodScreen(
    onNavigateBack: () -> Unit,
    onNavigateToQrSetup: () -> Unit,
    onNavigateToMathSetup: () -> Unit,
    onNavigateToShakeChallenge : () -> Unit,
    viewModel: ChallengeMethodViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val options = listOf(
        ChallengeOption(ChallengeType.DEFAULT, "Default", Icons.Default.Alarm, Color(0xFFE91E63)),
        ChallengeOption(ChallengeType.MATH, "Math Filter", Icons.Default.Calculate, Color(0xFFFF9800)),
        ChallengeOption(ChallengeType.QR_CODE, "QR Code", Icons.Default.QrCode, Color(0xFF03A9F4)),
        ChallengeOption(ChallengeType.SHAKE, "Shake", Icons.Default.Vibration, Color(0xFF9C27B0))
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Challenge Alarm") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "The Any Of The Method Set In Your Alarm",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // 2-column grid বানানো হচ্ছে LazyVerticalGrid দিয়ে
            androidx.compose.foundation.lazy.grid.LazyVerticalGrid(
                columns = androidx.compose.foundation.lazy.grid.GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(options) { option ->
                    ChallengeCard(
                        option = option,
                        isSelected = uiState.selectedType == option.type,
                        onClick = {
                            viewModel.onChallengeSelected(option.type)
//                            if (option.type == ChallengeType.QR_CODE) {
//                                onNavigateToQrSetup()
//                            }

                            when(option.type) {
                                ChallengeType.QR_CODE -> onNavigateToQrSetup()
                                ChallengeType.MATH -> onNavigateToMathSetup()
                                ChallengeType.SHAKE -> onNavigateToShakeChallenge()
                                else -> {}
                            }
                        }
                    )
                }
            }

            Button(
                onClick = { viewModel.saveChallenge() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text("Save Challenge Method")
            }
        }
    }
}

@Composable
fun ChallengeCard(
    option: ChallengeOption,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(16.dp))
            .background(option.color)
            .clickable { onClick() }
            .padding(12.dp)
    ) {
        if (isSelected) {
            Icon(
                Icons.Default.CheckCircle,
                contentDescription = "Selected",
                tint = Color.White,
                modifier = Modifier.align(Alignment.TopEnd)
            )
        }
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(option.icon, contentDescription = option.title, tint = Color.White, modifier = Modifier.size(40.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(option.title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }
    }
}

