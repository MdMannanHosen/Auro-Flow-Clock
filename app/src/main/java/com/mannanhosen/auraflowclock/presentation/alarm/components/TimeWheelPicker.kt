package com.mannanhosen.auraflowclock.presentation.alarm.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.abs

@Composable
fun TimeWheelPicker(
    items: List<String>,
    state: PagerState,
    modifier: Modifier = Modifier,
    isInfinite: Boolean = false
) {
    val flingBehavior = PagerDefaults.flingBehavior(
        state = state,
        pagerSnapDistance = PagerSnapDistance.atMost(50),
        snapPositionalThreshold = 0.01f
    )

    VerticalPager(
        state = state,
        modifier = modifier.height(250.dp).clipToBounds(),
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

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = items[itemIndex],
                style = TextStyle(
                    fontSize = 48.sp,
                    color = if (abs(pageOffset) < 0.2f) Color(0xFFDDDAD5) else Color(0x99C9C7C3),
                    fontWeight = if (abs(pageOffset) < 0.2f) FontWeight.ExtraBold else FontWeight.Medium
                )
            )
        }
    }
}