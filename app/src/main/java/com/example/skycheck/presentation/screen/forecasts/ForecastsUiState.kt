package com.example.skycheck.presentation.screen.forecasts

import com.example.skycheck.data.model.dto.ForecastDto
import com.example.skycheck.data.model.entity.Location

data class ForecastsUiState(
    val isLoadingLocations: Boolean = true,
    val locations: List<Location?> = emptyList(),
    val locationsForecasts: Map<Int?, ForecastDto?> = emptyMap(),
    val locationsNextDaysForecasts: Map<Int?, List<ForecastDto>?> = emptyMap(),
)