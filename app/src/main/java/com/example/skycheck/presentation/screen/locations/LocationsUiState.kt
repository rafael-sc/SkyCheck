package com.example.skycheck.presentation.screen.locations

import com.example.skycheck.data.model.dto.ForecastDto
import com.example.skycheck.data.model.dto.GeocodeLocationDto
import com.example.skycheck.data.model.entity.Location

data class LocationsUiState(
    val isLoadingLocations: Boolean = true,
    val locations: List<Location?> = emptyList(),
    val locationsForecasts: Map<Int?, ForecastDto?> = emptyMap(),
    val geocodeLocations: List<GeocodeLocationDto> = emptyList(),
    val isSearching: Boolean = false,
)