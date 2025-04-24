package com.example.skycheck.domain.repository

import com.example.skycheck.data.model.entity.Location

interface LocationRepository {

    suspend fun getCurrentLocation(): Location?

    suspend fun getSavedLocations(): List<Location>

    suspend fun saveLocation(location: Location): Long

    suspend fun deleteLocation(location: Location)
}