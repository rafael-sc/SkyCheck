package com.example.skycheck.data.model.dto

import com.google.gson.annotations.SerializedName

data class ForecastDto(
    val weather: List<Weather>,
    val main: MainForecast,
    val wind: Wind,
    val clouds: Clouds,
    @SerializedName("dt") val datetime: Long,
    val sys: SystemInfo,
    val timezone: Int,
    @SerializedName("name") val localityName: String,
    val cod: Int,
    @SerializedName("dt_txt") val datetimeText: String?
) {
    data class Weather(
        val main: String,
        val description: String,
        val icon: String,
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
