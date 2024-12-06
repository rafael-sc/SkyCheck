package com.example.skycheck.data.repository_impl

import com.example.skycheck.data.api.OpenWeatherApi
import com.example.skycheck.data.dto.CurrentForecastDto
import com.example.skycheck.domain.repository.OpenWeatherRepository
import retrofit2.Response

class OpenWeatherRepositoryImpl(
    private val openWeatherApi: OpenWeatherApi
) : OpenWeatherRepository {

    override suspend fun getCurrentForecast(
        lat: Double,
        lng: Double
    ): Response<CurrentForecastDto> {
        return openWeatherApi.getCurrentForecast(lat = lat, lng = lng)
    }
}