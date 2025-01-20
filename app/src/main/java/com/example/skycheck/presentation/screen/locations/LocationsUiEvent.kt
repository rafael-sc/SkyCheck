package com.example.skycheck.presentation.screen.locations

import android.content.Context
import com.example.skycheck.data.model.entity.Location

sealed class LocationsUiEvent {
    data object OnGetLocations : LocationsUiEvent()
    data object OnRequestCurrentLocation : LocationsUiEvent()
    data class OnSetFusedLocationProviderClient(val context: Context) : LocationsUiEvent()
    data class OnChangeLocation(val value: String) : LocationsUiEvent()
    data class OnSaveLocation(val location: Location) : LocationsUiEvent()
    data class OnDeleteLocation(val location: Location) : LocationsUiEvent()
}