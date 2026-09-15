package com.mannanhosen.auraflowclock.presentation.NavigationBar.Discover
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.mannanhosen.auraflowclock.R

private val BackgroundColor = Color(0xFF181A1B)
private val CardColor = Color(0xFF202224)
private val CardColorPressed = Color(0xFF26292B)
private val AccentTeal = Color(0xFF22D3EE)
private val MutedGray = Color(0xFF9E9E9E)


private fun buildDiscoverFeatures(navController: NavHostController): List<DiscoverFeature> {
    return listOf(
        DiscoverFeature(
            label = "Settings",
            icon = FeatureIcon.Vector(Icons.Default.Settings),
            onClick = {
                navController.navigate("settings_screen")
            }
        ),
        DiscoverFeature(
            label = "World Clock",
            icon = FeatureIcon.Drawable(R.drawable.alarm),
            onClick = {
                Log.d("lkajdfoj", "Find Bug")
                navController.navigate("WorldClockScreen")}
        ),
        DiscoverFeature(
            label = "Bed Time",
            icon = FeatureIcon.Drawable(R.drawable.alarm),
            onClick = {  }
        ),
        DiscoverFeature(
            label = "Box Breathing",
            icon = FeatureIcon.Drawable(R.drawable.home),
            onClick = { navController.navigate("box_breathing_screen") }
        ),
        DiscoverFeature(
            label = "Pomodoro",
            icon = FeatureIcon.Drawable(R.drawable.home),
            onClick = {  }
        ),
        DiscoverFeature(
            label = "Drinking Water",
            icon = FeatureIcon.Drawable(R.drawable.home),
            onClick = {  }
        )
    )
}

@Composable
fun DiscoverDisplay(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val features = remember(navController) { buildDiscoverFeatures(navController) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(horizontal = 20.dp)
    ) {

        VerticalSpace(20.dp)

        Text(
            text = "Discover",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = "More tools to help you through the day",
            color = MutedGray,
            fontSize = 14.sp
        )

        VerticalSpace(24.dp)

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            contentPadding = PaddingValues(bottom = 24.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(features) { feature ->
                DiscoverFeatureCard(feature)
            }
        }
    }
}

@Composable
private fun DiscoverFeatureCard(feature: DiscoverFeature) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    // ✅ chapleý card ta subtly scale-down hoy - premium, tactile feel er jonno
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.94f else 1f,
        animationSpec = tween(120),
        label = "card_press_scale"
    )

    Surface(
        onClick = feature.onClick,
        interactionSource = interactionSource,
        shape = RoundedCornerShape(20.dp),
        color = if (isPressed) CardColorPressed else CardColor,
        modifier = Modifier
            .aspectRatio(1f)
            .fillMaxWidth()
            .graphicsLayer(scaleX = scale, scaleY = scale)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(AccentTeal.copy(alpha = 0.12f), RoundedCornerShape(14.dp)),
                contentAlignment = Alignment.Center
            ) {
                when (val icon = feature.icon) {
                    is FeatureIcon.Drawable -> Icon(
                        painter = painterResource(id = icon.resId),
                        contentDescription = feature.label,
                        tint = AccentTeal,
                        modifier = Modifier.size(22.dp)
                    )
                    is FeatureIcon.Vector -> Icon(
                        imageVector = icon.imageVector,
                        contentDescription = feature.label,
                        tint = AccentTeal,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }

            VerticalSpace(10.dp)

            Text(
                text = feature.label,
                color = Color(0xFFDDDAD5),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                maxLines = 2
            )
        }
    }
}

@Composable
private fun VerticalSpace(height: Dp) {
    androidx.compose.foundation.layout.Spacer(modifier = Modifier.size(0.dp, height))
}
