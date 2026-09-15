package com.mannanhosen.auraflowclock.presentation.NavigationBar.Discover


import androidx.compose.ui.graphics.vector.ImageVector


data class DiscoverFeature(
    val label: String,
    val icon: FeatureIcon,
    val onClick: () -> Unit
)


sealed class FeatureIcon {
    data class Drawable(val resId: Int) : FeatureIcon()
    data class Vector(val imageVector: ImageVector) : FeatureIcon()
}
