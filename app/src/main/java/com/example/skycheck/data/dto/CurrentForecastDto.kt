package com.example.skycheck.data.dto

import com.google.gson.annotations.SerializedName

data class CurrentForecastDto(
    val weather: List<Weather>,
    val main: MainForecast,
    val wind: Wind,
    val clouds: Clouds,
    @SerializedName("dt") val datetime: Long,
    val sys: SystemInfo,
    val timezone: Int,
    @SerializedName("name") val localityName: Long,
    val cod: Int
) {
    data class Weather(
        val main: String,
        val description: String,
    )

    data class MainForecast(
        val temp: Double,
        @SerializedName("feels_like") val feelsLike: Double,
        @SerializedName("temp_min") val minTemperature: Double,
        @SerializedName("temp_max") val maxTemperature: Double,
        val humidity: Int
    )

    data class Wind(
        val speed: Double
    )

    data class Clouds(
        val all: Int
    )

    data class SystemInfo(
        val sunrise: Long,
        val sunset: Long,
    )
}
