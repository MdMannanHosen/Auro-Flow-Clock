package com.mannanhosen.auraflowclock.presentation.NavigationBar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.mannanhosen.auraflowclock.presentation.NavigationBar.Discover.DiscoverDisplay
import com.mannanhosen.auraflowclock.presentation.NavigationBar.HomeScreen.HomeDisplay
import com.mannanhosen.auraflowclock.presentation.NavigationBar.Timer.AlarmDisplay
import com.mannanhosen.auraflowclock.presentation.NavigationBar.TimerScreen.TimerScreenDisplay
import com.mannanhosen.auraflowclock.R
import com.mannanhosen.auraflowclock.presentation.add_edit_alarm.screen.AddEditAlarmScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {

    val navItemList = listOf(
        NaviItems("Alarms", R.drawable.alarm),
        NaviItems("Timers", R.drawable.timer),
        NaviItems("Stop Watch", R.drawable.home),
        NaviItems("Discover", R.drawable.discover)
    )

    // ✅ ModalBottomSheet সরানো হয়েছে - "Discover" এখন বাকি ট্যাবগুলোর মতোই
    //    একটা normal full screen (index 3), তাই selectedIndex-ই যথেষ্ট।
    //    আলাদা isDiscoverActive/showSheet state আর দরকার নেই।
    var selectedIndex by remember { mutableIntStateOf(0) }

    var showCreateAlarm by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),

        floatingActionButtonPosition = FabPosition.End,

        floatingActionButton = {
            if (selectedIndex == 0) {
                FloatingActionButton(
                    onClick = {
                        navController.navigate("create_alarm")
                    },
                    containerColor = Color(0xFF100F0F),
                    contentColor = Color(0xFFDDDAD5),
                    elevation = FloatingActionButtonDefaults.elevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 0.dp
                    ),
                    modifier = Modifier.shadow(
                        elevation = 10.dp,
                        shape = CircleShape,
                        ambientColor = Color.White,
                        spotColor = Color.White
                    )
                ) {
                    GooglePlusIcon(modifier = Modifier.size(24.dp))
                }
            }
        },

        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFF181A1B),
                modifier = Modifier.border(
                    width = 1.dp,
                    color = Color(0x4AC9C7C3)
                )
            ) {

                navItemList.forEachIndexed { index, naviItem ->

                    val isSelected = selectedIndex == index

                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { selectedIndex = index },
                        icon = {
                            Icon(
                                painter = painterResource(id = naviItem.iconRes),
                                contentDescription = naviItem.label,
                                modifier = Modifier.size(24.dp),
                                tint = if (isSelected)
                                    Color(0xFFDDDAD5)
                                else
                                    Color(0x99C9C7C3)
                            )
                        },
                        label = { Text(naviItem.label) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFFDDDAD5),
                            selectedTextColor = Color(0xFFDDDAD5),
                            unselectedIconColor = Color(0x99C9C7C3),
                            unselectedTextColor = Color(0x99C9C7C3),
                            indicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }
    ) { innerPadding ->

        ContentScreen(
            modifier = Modifier.padding(innerPadding),
            selectedIndex = selectedIndex,
            navController = navController
        )
    }

    if (showCreateAlarm) {
        Dialog(
            onDismissRequest = { showCreateAlarm = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            AddEditAlarmScreen(
                navController = navController,
                onCancel = { showCreateAlarm = false },
                onSave = { showCreateAlarm = false }
            )
        }
    }
}

@Composable
fun ContentScreen(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    navController: NavHostController
) {
    when (selectedIndex) {
        0 -> HomeDisplay()
        1 -> AlarmDisplay()
        2 -> TimerScreenDisplay()
        // ✅ notun - "Discover" ekhon normal full screen, ModalBottomSheet na
        3 -> DiscoverDisplay(navController = navController)
    }
}

@Composable
fun GooglePlusIcon(modifier: Modifier = Modifier) {

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column {
            Box(Modifier.size(width = 4.dp, height = 10.dp).background(Color(0xFF3571EA)))
            Box(Modifier.size(width = 4.dp, height = 10.dp).background(Color(0xFF34A853)))
        }
        Row {
            Box(Modifier.size(width = 10.dp, height = 4.dp).background(Color(0xFFFBBC05)))
            Box(Modifier.size(width = 10.dp, height = 4.dp).background(Color(0xFF4285F4)))
        }
    }
}
