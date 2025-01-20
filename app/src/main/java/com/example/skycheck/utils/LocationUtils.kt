package com.example.skycheck.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.example.skycheck.data.model.entity.Location

fun hasLocationPermission(context: Context): Boolean {
    return (
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        ||
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    )
}

fun isCurrentUserLocationInList(locations: List<Location>): Boolean {
    for (location in locations) {
        if (location.isCurrentUserLocality) {
            return true
        }
    }
    return false
}

fun formUserLocationsList(currentUserLocation: Location, userLocations: List<Location>) : List<Location> {
    var filteredLocations = userLocations

    if (isCurrentUserLocationInList(userLocations)) {
        filteredLocations = userLocations.filter { !it.isCurrentUserLocality }
    }

    return listOf(currentUserLocation) + filteredLocations
}