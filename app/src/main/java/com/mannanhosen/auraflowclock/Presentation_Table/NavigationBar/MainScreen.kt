package com.mannanhosen.auraflowclock.Presentation_Table.NavigationBar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.runtime.*
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mannanhosen.auraflowclock.Presentation_Table.NavigationBar.Alarm.AlarmDisplay
import com.mannanhosen.auraflowclock.Presentation_Table.NavigationBar.Discover.DiscoverDisplay
import com.mannanhosen.auraflowclock.Presentation_Table.NavigationBar.HomeScreen.HomeDisplay

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreen(modifier: Modifier = Modifier) {

   val navItemList = listOf(
       NaviItems("Home", Icons.Default.Home),
       NaviItems("Alarms", Icons.Default.Notifications),
       NaviItems("Timers", Icons.Default.Delete),
       NaviItems("Discover", Icons.Default.Warning)
   )

    var selectedIndex by remember { mutableIntStateOf(0) }


    Scaffold(modifier = Modifier
        .fillMaxSize(),
        bottomBar = {
            NavigationBar (
                containerColor = Color(0xFF181A1B),
                modifier = Modifier.border(width = 1.dp,
                    color = Color(0x4AC9C7C3)
                )

            ) {
            navItemList.forEachIndexed { index, naviItems ->
                NavigationBarItem(
                    selected = selectedIndex == index,
                    {
                        selectedIndex = index
                    },
                    {
                        Icon(imageVector = naviItems.icon,
                            contentDescription = "Icon",
                            )
                    },
                    label = {
                        Text(text = naviItems.label)
                    },
                    colors = NavigationBarItemDefaults.colors(

                        selectedIconColor = Color(0xFFDDDAD5),
                        selectedTextColor = Color(0xFFDDDAD5),

                        // ✅ Unselected (Inactive Item)
                        unselectedIconColor = Color(0x99C9C7C3),
                        unselectedTextColor = Color(0x99C9C7C3),

                        // ✅ No ugly background highlight
                        indicatorColor = Color.Transparent
                    )

                )



            }
            }
        }
    ) { innerPadding ->
  ContentScreen(modifier = Modifier.padding(innerPadding), selectedIndex)
    }
}

@Composable
fun ContentScreen(modifier: Modifier = Modifier, selectedIndex : Int) {

  when(selectedIndex) {
      0-> HomeDisplay()
      1 -> AlarmDisplay()
      2 -> DiscoverDisplay()
  }
}