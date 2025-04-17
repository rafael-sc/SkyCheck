package com.example.skycheck.domain.repository

import com.example.skycheck.data.model.entity.Location

interface LocationRepository {

    suspend fun getCurrentLocation(): Location?

    suspend fun getSavedLocations(): List<Location>
}