package com.example.skycheck.presentation.screen.current_location

import android.content.Context

sealed class CurrentLocationUiEvent {
    data object OnRequestCurrentLocation : CurrentLocationUiEvent()
    data object OnGetUserLocations : CurrentLocationUiEvent()
    data class OnSetFusedLocationProviderClient(val context: Context) : CurrentLocationUiEvent()
}