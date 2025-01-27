package com.example.skycheck.presentation.screen.current_location

import com.example.skycheck.data.model.dto.ForecastDto
import com.example.skycheck.data.model.entity.Location

data class CurrentLocationUiState(
    val isLoadingLocations: Boolean = true,
    val userLocations: List<Location> = emptyList(),
    val locationsForecasts: Map<Int?, ForecastDto?> = emptyMap(),
    val currentUserLocation: Location? = null,
    val currentMainImageForecast: MainImageForecastDimensions? = null
)