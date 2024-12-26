package com.example.skycheck.utils

import com.example.skycheck.R

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