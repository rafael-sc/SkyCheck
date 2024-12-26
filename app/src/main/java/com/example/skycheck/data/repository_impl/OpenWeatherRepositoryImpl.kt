package com.example.skycheck.data.repository_impl

import com.example.skycheck.R
import com.example.skycheck.data.api.OpenWeatherApi
import com.example.skycheck.data.model.dto.ForecastDto
import com.example.skycheck.data.model.dto.GeocodeLocationDto
import com.example.skycheck.data.model.dto.Next5DaysForecastDto
import com.example.skycheck.domain.repository.OpenWeatherRepository
import com.example.skycheck.utils.Constants.OPEN_WEATHER_API_KEY_RES
import retrofit2.Response

class OpenWeatherRepositoryImpl(
    private val openWeatherApi: OpenWeatherApi
) : OpenWeatherRepository {

    override suspend fun getCurrentForecast(
        lat: Double,
        lng: Double
    ): Response<ForecastDto> {
        return openWeatherApi.getCurrentForecast(
            lat = lat,
            lon = lng,
            apiKey = OPEN_WEATHER_API_KEY_RES
        )
    }

    override suspend fun getNext5DaysForecast(
        lat: Double,
        lng: Double
    ): Response<Next5DaysForecastDto> {
        return openWeatherApi.getNext5DaysForecast(
            lat = lat,
            lon = lng,
            apiKey = OPEN_WEATHER_API_KEY_RES
        )
    }

    override suspend fun getGeocodeFromText(
        query: String,
        limit: Int
    ): Response<List<GeocodeLocationDto>> {
        return openWeatherApi.getGeocodeFromText(
            query = query,
            limit = limit,
//            apiKey = R.string.open_weather_api_key
        )
    }
}