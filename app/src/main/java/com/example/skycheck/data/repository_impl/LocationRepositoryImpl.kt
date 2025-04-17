package com.example.skycheck.data.repository_impl

import android.annotation.SuppressLint
import android.util.Log
import com.example.skycheck.data.model.dao.LocationDao
import com.example.skycheck.data.model.entity.Location
import com.example.skycheck.domain.repository.LocationRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.flow.first
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LocationRepositoryImpl(
    private val fusedLocationClient: FusedLocationProviderClient,
    private val locationDao: LocationDao
) : LocationRepository {
    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): Location? = suspendCoroutine { continuation ->
        val tokenSource = CancellationTokenSource()
        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, tokenSource.token)
            .addOnSuccessListener { location ->
                if (location != null) {
                    val locationResult = Location(
                        locality = "Meu Local",
                        latitude = location.latitude,
                        longitude = location.longitude,
                        isCurrentUserLocality = true
                    )
                    continuation.resume(value = locationResult)
                } else {
                    Log.e("currentLocation", "getCurrentLocation returned null")
                    continuation.resume(value = null)
                }
            }
            .addOnFailureListener { exception ->
                Log.e("currentLocation", "getCurrentLocation failed", exception)
                continuation.resume(value = null)
            }
    }

    override suspend fun getSavedLocations(): List<Location> {
        return try {
            val locations = locationDao.getLocations().first()
            locations
        } catch (e: Exception) {
            Log.e(
                "currentLocation",
                "Error when fetching user locations: ${e.message}",
                e
            )
            emptyList()
        }
    }
}