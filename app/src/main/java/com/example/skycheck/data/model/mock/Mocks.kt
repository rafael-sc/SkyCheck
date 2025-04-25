package com.example.skycheck.data.model.mock

import com.example.skycheck.data.model.dto.ForecastDto
import com.example.skycheck.data.model.entity.Location

val locationsPageMock = listOf(
    Location(
        id = 1,
        locality = "São Paulo",
        latitude = -23.5506507,
        longitude = -46.6333824,
        isCurrentUserLocality = true
    ),
    Location(
        id = 2,
        locality = "Taubaté",
        latitude = -23.031448,
        longitude = -45.5612792,
        isCurrentUserLocality = false
    ),
    Location(
        id = 3,
        locality = "Gold Coast",
        latitude = -28.0023731,
        longitude = 153.4145987,
        isCurrentUserLocality = false
    ),
)

val mockForecast = ForecastDto(
    weather = listOf(
        ForecastDto.Weather(
            main = "Rain",
            description = "light rain",
            icon = "10n"
        )
    ),
    main = ForecastDto.MainForecast(
        temp = 285.87,
        feelsLike = 285.42,
        minTemperature = 285.87,
        maxTemperature = 286.16,
        humidity = 85
    ),
    wind = ForecastDto.Wind(
        speed = 2.56
    ),
    clouds = ForecastDto.Clouds(
        all = 52
    ),
    datetime = 1745528400L,
    sys = ForecastDto.SystemInfo(
        sunrise = 1745528400L,
        sunset = 1745528400L
    ),
    timezone = 0,
    localityName = "Nome da Localidade",
    cod = 200,
    datetimeText = "2025-04-24 21:00:00"
)
