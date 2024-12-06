package com.example.skycheck.data.api

import com.example.skycheck.data.dto.CurrentForecastDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface OpenWeatherApi {

    @GET("weather{lat}&{lon}=&{appid}&lang=pt_br")
    suspend fun getCurrentForecast(
        @Path("lat") lat: Double = 44.34,
        @Path("lon") lng: Double = 10.99,
        @Path("appid") apiKey: String = "ee41eb22779e47d46a521eda6302aacd",
    ): Response<CurrentForecastDto>
}