package com.example.skycheck.utils

import com.example.skycheck.presentation.screen.forecasts.MainCardImageDimensions
import com.example.skycheck.presentation.screen.forecasts.MainImageForecastDimensions

fun getImageOfForecast(openWeatherIconId: String): MainImageForecastDimensions {
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