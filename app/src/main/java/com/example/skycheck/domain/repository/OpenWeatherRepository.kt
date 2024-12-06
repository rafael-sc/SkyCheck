package com.example.skycheck.domain.repository

import com.example.skycheck.data.dto.CurrentForecastDto
import retrofit2.Response

interface OpenWeatherRepository {

    suspend fun getCurrentForecast(
        lat: Double,
        lng: Double,
    ) : Response<CurrentForecastDto>
}