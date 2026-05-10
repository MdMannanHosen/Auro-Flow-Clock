package com.mannanhosen.auraflowclock.Presentation_Table.NavigationBar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mannanhosen.auraflowclock.Presentation_Table.NavigationBar.Alarm.AlarmDisplay
import com.mannanhosen.auraflowclock.Presentation_Table.NavigationBar.HomeScreen.HomeDisplay
import com.mannanhosen.auraflowclock.Presentation_Table.NavigationBar.TimerScreen.TimerScreenDisplay
import com.mannanhosen.auraflowclock.R

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreen(modifier: Modifier = Modifier) {

    val navItemList = listOf(

        NaviItems("Alarms", R.drawable.alarm),
        NaviItems("Timers", R.drawable.timer),
        NaviItems("Stop Watch", R.drawable.home),
        NaviItems("Discover", R.drawable.discover)
    )

    var selectedIndex by remember { mutableIntStateOf(0) }
    var showSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    var showCreateAlarm by remember { mutableStateOf(false) }



    Scaffold(
        modifier = Modifier.fillMaxSize(),

        floatingActionButtonPosition = FabPosition.End, // এটি ডিফল্ট, চাইলে Center দিতে পারেন

        // Floating Button
        floatingActionButton = {
            if(selectedIndex == 0) {
                FloatingActionButton(

                    onClick = {
                        showCreateAlarm =true //ata ki kore call dite pari
                    },
                    containerColor = Color(0xFF100F0F),
                    contentColor = Color(0xFFDDDAD5),
                    elevation = FloatingActionButtonDefaults.elevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 0.dp
                    ),

                    modifier = Modifier. shadow(
                        elevation = 10.dp,
                        shape = CircleShape,
                        ambientColor = Color.White,
                        spotColor = Color.White
                    )

                ) {
//                 Icon(
//                     imageVector = Icons.Rounded.Add,
//                     contentDescription = "Add Alarm",
//                     modifier = Modifier.size(28.dp)
//                 )

                    GooglePlusIcon(modifier = Modifier.size(24.dp) )


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

                    NavigationBarItem(
//                        selected = selectedIndex == index,
                        selected = if (index ==3 ) false
                        else selectedIndex == index,

                        onClick = {
                            if (index == 3) {
                                showSheet = true
                                selectedIndex = -1
                            } else {
                                selectedIndex = index
                            }
                        },

                        icon = {
                            Icon(
                                painter = painterResource(id = naviItem.iconRes),
                                contentDescription = naviItem.label,
                                modifier = Modifier.size(24.dp),
                                tint =
                                    if (selectedIndex == index && index != 3)
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
            selectedIndex
        )

        // ✅ Bottom Sheet
        if (showSheet) {
            ModalBottomSheet(
                onDismissRequest = { showSheet = false },
                sheetState = sheetState,
                containerColor = Color(0xFF202224), // ⭐ IMPORTANT
                scrimColor = Color.Black.copy(alpha = 0.10f),
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
//                dragHandle = {
//                    BottomSheetDefaults.DragHandle(
//                        color = Color.Gray.copy(alpha = 0.5f)
//                    )
//                }
            ) {

                Surface(
                    modifier = Modifier.fillMaxWidth()
                        .navigationBarsPadding(),
                    shape = RoundedCornerShape(
                        topStart = 32.dp,
                        topEnd = 32.dp
                    ),
                    color = Color(0xFF202224),

                    ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState())
                            .padding(start = 24.dp, end = 24.dp, top = 8.dp, bottom = 32.dp),
//                            .padding(horizontal = 24.dp),

                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Discover More Features",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White.copy(alpha = 0.9f)
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // Start Here

                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(
                                24.dp,
                                Alignment.CenterHorizontally
                            ),
                            verticalArrangement = Arrangement.spacedBy(24.dp),
                            maxItemsInEachRow = 3   // ⭐ প্রতি লাইনে কয়টা দেখাবে
                        ) {

                            FeatureIconItem(
                                label = "Bed Time",
                                iconRes = R.drawable.alarm
                            ) {}

                            FeatureIconItem(
                                label = "Box Breathing",
                                iconRes = R.drawable.home
                            ) {}

                            FeatureIconItem(
                                label = "Drinking Water",
                                iconRes = R.drawable.home
                            ) {}

                            FeatureIconItem(
                                label = "Drinking Water",
                                iconRes = R.drawable.home
                            ) {}

                            FeatureIconItem(
                                label = "Drinking Water",
                                iconRes = R.drawable.home
                            ) {}

                            FeatureIconItem(
                                label = "Drinking Water",
                                iconRes = R.drawable.home
                            ) {}
                        }
                    }
                }
            }
        }
        // end here
    }
}

@Composable
fun ContentScreen(
    modifier: Modifier = Modifier,
    selectedIndex: Int
) {
    when (selectedIndex) {
        0 -> HomeDisplay()
        1 -> AlarmDisplay()
        2 -> TimerScreenDisplay()
    }
}

@Composable
fun FeatureIconItem(
    label: String,
    iconRes: Int,
    onClick: () -> Unit
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(90.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(8.dp)
    ) {

        Box(
            modifier = Modifier
                .size(64.dp)
                .background(
                    Color(0xFF2D3133),
                    RoundedCornerShape(16.dp)
                ),
            contentAlignment = Alignment.Center
        ) {

            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = label,
                tint = Color(0xFFDDDAD5),
                modifier = Modifier.size(32.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = Color(0xFFDDDAD5),
            textAlign = TextAlign.Center
        )
    }

}
@Composable
fun GooglePlusIcon(modifier: Modifier = Modifier) {


    Box(
        modifier = modifier
        ,contentAlignment = Alignment.Center
    ) {
        // Vertical Bar (লাল এবং সবুজ)
        Column {
            Box(Modifier.size(width = 4.dp, height = 10.dp).background(Color(0xFF3571EA)))
            Box(Modifier.size(width = 4.dp, height = 10.dp).background(Color(0xFF34A853)))
        }
        // Horizontal Bar (হলুদ এবং নীল)
        Row {
            Box(Modifier.size(width = 10.dp, height = 4.dp).background(Color(0xFFFBBC05)))
            Box(Modifier.size(width = 10.dp, height = 4.dp).background(Color(0xFF4285F4)))
        }
    }
}

