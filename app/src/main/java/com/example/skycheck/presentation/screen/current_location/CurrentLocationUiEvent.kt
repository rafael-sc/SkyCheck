package com.example.skycheck.presentation.screen.current_location

import android.content.Context
import com.example.skycheck.data.model.entity.Location

sealed class CurrentLocationUiEvent {
    data class OnGetCurrentForecast(val location: Location) : CurrentLocationUiEvent()
    data object OnRequestCurrentLocation : CurrentLocationUiEvent()
    data object OnGetUserLocations : CurrentLocationUiEvent()
    data class OnSetFusedLocationProviderClient(val context: Context) : CurrentLocationUiEvent()
}