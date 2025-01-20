package com.example.skycheck.presentation.screen.current_location

import com.example.skycheck.data.model.dto.ForecastDto
import com.example.skycheck.data.model.entity.Location

data class CurrentLocationUiState(
    val isLoadingLocations: Boolean = false,
    val isFetchingForecast: Boolean = false,
    val userLocations: List<Location> = emptyList(),
    val currentForecastData: ForecastDto? = null,
    val currentUserLocation: Location? = null,
    val currentMainImageForecast: MainImageForecastDimensions? = null
)