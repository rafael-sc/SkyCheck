package com.example.skycheck.presentation.screen.current_location

import androidx.compose.ui.unit.dp
import com.example.skycheck.R

object MainCardImageDimensions {
    val clouds = MainImageForecastDimensions(
        imgRes = R.drawable.img_clouds,
        size = 160.dp,
        offset = (-56).dp
    )
    val cloudy = MainImageForecastDimensions(
        imgRes = R.drawable.img_cloudy,
        size = 160.dp,
        offset = (-52).dp
    )
    val moonStars = MainImageForecastDimensions(
        imgRes = R.drawable.img_moon_stars,
        size = 144.dp,
        offset = (-36).dp
    )
    val rain = MainImageForecastDimensions(
        imgRes = R.drawable.img_rain,
        size = 152.dp,
        offset = (-36).dp
    )
    val subRain = MainImageForecastDimensions(
        imgRes = R.drawable.img_sub_rain,
        size = 140.dp,
        offset = (-28).dp
    )
    val sun = MainImageForecastDimensions(
        imgRes = R.drawable.img_sun,
        size = 180.dp,
        offset = (-56).dp,
        hasHorizontalPadding = false
    )
    val thunder = MainImageForecastDimensions(
        imgRes = R.drawable.img_thunder,
        size = 152.dp,
        offset = (-36).dp
    )
}