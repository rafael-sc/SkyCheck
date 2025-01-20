package com.example.skycheck.data.api

import com.example.skycheck.data.model.dto.ForecastDto
import com.example.skycheck.data.model.dto.GeocodeLocationDto
import com.example.skycheck.data.model.dto.Next5DaysForecastDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {

    @GET("data/2.5/weather")
    suspend fun getCurrentForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String = "ee41eb22779e47d46a521eda6302aacd",
        @Query("lang") lang: String = "pt_br"
    ): Response<ForecastDto>

    @GET("data/2.5/forecast")
    suspend fun getNext5DaysForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String = "ee41eb22779e47d46a521eda6302aacd",
        @Query("lang") lang: String = "pt_br"
    ): Response<Next5DaysForecastDto>

    @GET("geo/1.0/direct")
    suspend fun getGeocodeFromText(
        @Query("q") query: String,
        @Query("limit") limit: Int,
//        @Query("appid") @StringRes apiKey: Int,
        @Query("appid") apiKey: String = "ee41eb22779e47d46a521eda6302aacd",
        @Query("lang") lang: String = "pt_br",
    ): Response<List<GeocodeLocationDto>>
}