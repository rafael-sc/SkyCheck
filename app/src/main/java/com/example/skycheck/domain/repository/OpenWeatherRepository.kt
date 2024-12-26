package com.example.skycheck.domain.repository

import com.example.skycheck.data.model.dto.ForecastDto
import com.example.skycheck.data.model.dto.GeocodeLocationDto
import com.example.skycheck.data.model.dto.Next5DaysForecastDto
import retrofit2.Response

interface OpenWeatherRepository {
    suspend fun getCurrentForecast(
        lat: Double,
        lng: Double,
    ) : Response<ForecastDto>

    suspend fun getNext5DaysForecast(
        lat: Double,
        lng: Double,
    ) : Response<Next5DaysForecastDto>

    suspend fun getGeocodeFromText(
        query: String,
        limit: Int = 2,
    ) : Response<List<GeocodeLocationDto>>
}