package com.mannanhosen.auraflowclock.Presentation_Table.alarm

import android.R.attr.fontWeight
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat.startActivity
import kotlin.math.abs
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAlarmScreen(
    onCancel: () -> Unit = {},
    onSave: () -> Unit = {}
) {


    Column(
        modifier = Modifier.fillMaxSize()
            .background(Color(0xFF202224))
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .weight(1f)
                .padding(16.dp)
        ) {

            val hours = remember { (1..12).map {it.toString()} }
            val minutes = remember { (0..59).map { it.toString().padStart(2, '0') } }
            val periods = remember { listOf("am", "pm") }

            val startIndex = 1000
             val  hourState = rememberPagerState (
                 pageCount = {Int.MAX_VALUE},
            initialPage =  startIndex *hours.size + 10

             )

            val minutesState = rememberPagerState (
                pageCount = { Int.MAX_VALUE},
                initialPage = startIndex * minutes.size + 0
            )



            val periodState = rememberPagerState (
                pageCount = {periods.size}

            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                TimeWheelPicker(
                    items = hours,
                    state = hourState,
                    modifier = Modifier.weight(1f),
                    isInfinite =  true
                )

                Text(
                    text = ":",
                    style = TextStyle(fontSize = 50.sp, fontWeight  = FontWeight.Bold,
                        color = Color(0xFFDDDAD5)),
                    modifier = Modifier.padding(horizontal = 4.dp)

                )

                Spacer(modifier = Modifier.width(12.dp))

                TimeWheelPicker(
                    items = minutes,
                    state = minutesState,
                    modifier = Modifier.weight(1f),
                    isInfinite = true
                )

                Spacer(modifier = Modifier.width(12.dp))

                TimeWheelPicker(
                    items = periods,
                    state = periodState,
                    modifier = Modifier.weight(0.8f),
                    isInfinite = false
                )

                Spacer(
                    modifier = Modifier.width(12.dp)
                )

            }





        }
    }

}
@Composable
fun TimeWheelPicker(
    items : List<String>,
    state: PagerState,
    modifier: Modifier = Modifier,
    isInfinite : Boolean = false
) {


    val flingBehavior = PagerDefaults.flingBehavior(
        state = state,
        pagerSnapDistance = PagerSnapDistance.atMost(50),
        snapPositionalThreshold = 0.1f
    )

    VerticalPager(

        state = state,
        beyondViewportPageCount = 1,
        modifier =  modifier.height(250.dp),
        contentPadding = PaddingValues(vertical = 80.dp),
        flingBehavior = flingBehavior

    )  { page ->


 val itemIndex = if( isInfinite) page % items.size else page

  val pageOffset = remember (state, page) {
      derivedStateOf {
          val offset = (state.currentPage - page) + state.currentPageOffsetFraction
          offset.coerceIn(-1f, 1f)
      }
  }.value

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = items[itemIndex],
            fontWeight = FontWeight.W900,
            style = TextStyle(
                fontSize = 48.sp,
                color = if(abs(pageOffset) <0.2f) Color(0xFFDDDAD5) else Color(0x99C9C7C3),
                fontWeight =  if(abs(pageOffset) < 0.2f) FontWeight.ExtraBold else FontWeight.Medium
            )
        )
    }

    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4_XL)
@Composable
fun PreviewCreateAlarmScreen() {
    CreateAlarmScreen()
}