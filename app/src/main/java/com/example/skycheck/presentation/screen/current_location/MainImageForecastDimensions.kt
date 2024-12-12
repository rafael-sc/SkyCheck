package com.example.skycheck.presentation.screen.current_location

import androidx.annotation.DrawableRes
import androidx.compose.ui.unit.Dp

data class MainImageForecastDimensions(
    @DrawableRes val imgRes: Int,
    val size: Dp,
    val offset: Dp,
    val hasHorizontalPadding: Boolean = true
)
