package com.example.skycheck.utils

import com.example.skycheck.R
import com.example.skycheck.presentation.screen.forecasts.MainCardImageDimensions
import com.example.skycheck.presentation.screen.forecasts.MainImageForecastDimensions

fun getImageOfForecastForMainCard(openWeatherIconId: String): MainImageForecastDimensions {
    return when (openWeatherIconId) {
        "01d" -> MainCardImageDimensions.sun
        "02d" -> MainCardImageDimensions.cloudy
        "03d" -> MainCardImageDimensions.clouds
        "04d" -> MainCardImageDimensions.rain
        "09d" -> MainCardImageDimensions.rain
        "10d" -> MainCardImageDimensions.subRain
        "11d" -> MainCardImageDimensions.thunder
        "13d" -> MainCardImageDimensions.clouds
        "50d" -> MainCardImageDimensions.clouds
        else -> MainCardImageDimensions.moonStars
    }
}

fun getImageOfForecast(openWeatherIconId: String): Int {
    return when (openWeatherIconId) {
        "01d" -> R.drawable.img_sun
        "02d" -> R.drawable.img_cloudy
        "03d" -> R.drawable.img_clouds
        "04d" -> R.drawable.img_rain
        "09d" -> R.drawable.img_rain
        "10d" -> R.drawable.img_sub_rain
        "11d" -> R.drawable.img_thunder
        "13d" -> R.drawable.img_clouds
        "50d" -> R.drawable.img_clouds
        else -> R.drawable.img_moon_stars
    }
}