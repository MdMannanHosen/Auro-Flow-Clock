package com.mannanhosen.auraflowclock.Presentation_Table.alarm

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.abs

@Preview(showBackground = true, device = Devices.PIXEL_4_XL)
@Composable
fun CreateAlarmScreen() {

    val hours = remember { (1..12).map { it.toString() } }
    val minutes = remember { (0..59).map { it.toString().padStart(2, '0') } }
    val periods = remember { listOf("am", "pm") }

    val startIndex = 1000

    val hourState = rememberPagerState(
        pageCount = { Int.MAX_VALUE },
        initialPage = startIndex * hours.size + 10
    )
    val minuteState = rememberPagerState(
        pageCount = { Int.MAX_VALUE },
        initialPage = startIndex * minutes.size + 0
    )
    val periodState = rememberPagerState(pageCount = { periods.size })

    Column(
        modifier = Modifier.fillMaxSize()
            .background(color = Color(0xFF202224))

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            TimeWheelPicker(
                items = hours,
                state = hourState,
                modifier = Modifier.weight(1f),
                isInfinite = true
            )

            Text(
                text = ":",
                style = TextStyle(fontSize = 50.sp, fontWeight = FontWeight.Bold, color = Color(0xFFDDDAD5)),
                modifier = Modifier.padding(horizontal = 4.dp)
            )

            TimeWheelPicker(
                items = minutes,
                state = minuteState,
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
        }
    }
}

@SuppressLint("FrequentlyChangingValue")
@Composable
fun TimeWheelPicker(
    items: List<String>,
    state: PagerState,
    modifier: Modifier = Modifier,
    isInfinite: Boolean = false
) {

    val flingBehavior = PagerDefaults.flingBehavior(
        state = state,
        pagerSnapDistance = PagerSnapDistance.atMost(50), // এক টানে সর্বোচ্চ ১০টি ঘর পার হবে
        snapPositionalThreshold = 0.01f // হালকা টানেই স্ক্রল শুরু হবে
    )

    VerticalPager(
        state = state,
        modifier = modifier.height(250.dp),
        contentPadding = PaddingValues(vertical = 80.dp),
        flingBehavior = flingBehavior,
        beyondViewportPageCount = 1
    ) { page ->

        val itemIndex = if (isInfinite) page % items.size else page

        val pageOffset = remember(state, page) {
            derivedStateOf {
                val offset = (state.currentPage - page) + state.currentPageOffsetFraction
                offset.coerceIn(-1f, 1f)
            }
        }.value

        // ভিজ্যুয়াল ইফেক্টস (৩ডি ড্রাম লুক)
        val scale = 1f - (abs(pageOffset) * 0.3f)
        val alpha = 1f - (abs(pageOffset) * 0.7f)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
//                    scaleX = scale
//                    scaleY = scale
//                    this.alpha = alpha
//                    // রোটেশন দিলে এটি ঘোরানো চাকার মতো মনে হবে
//                    rotationX = pageOffset * 35f
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = items[itemIndex],
                fontWeight = FontWeight.W900,
                style = TextStyle(
                    fontSize = 50.sp,
                    color = if (abs(pageOffset) < 0.2f) Color(0xFFDDDAD5) else Color(0x99C9C7C3),
                    fontWeight = if (abs(pageOffset) < 0.2f) FontWeight.ExtraBold else FontWeight.Medium
                )
            )
        }
    }
}