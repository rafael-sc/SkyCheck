package com.example.skycheck.presentation.screen.current_location

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skycheck.data.model.dao.LocationDao
import com.example.skycheck.data.model.dto.ForecastDto
import com.example.skycheck.data.model.entity.Location
import com.example.skycheck.data.repository_impl.OpenWeatherRepositoryImpl
import com.example.skycheck.utils.formUserLocationsList
import com.example.skycheck.utils.getImageOfForecast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CurrentLocationViewModel(
    private val openWeatherRepository: OpenWeatherRepositoryImpl,
    private val locationDao: LocationDao
) : ViewModel() {

    val uiState = MutableStateFlow(CurrentLocationUiState())
    private var _fusedLocationClient: FusedLocationProviderClient? = null

    fun onEvent(event: CurrentLocationUiEvent) {
        when (event) {
            is CurrentLocationUiEvent.OnRequestCurrentLocation -> requestCurrentLocation()
            CurrentLocationUiEvent.OnGetUserLocations -> getUserLocations()
            is CurrentLocationUiEvent.OnSetFusedLocationProviderClient -> setFusedLocationProviderClient(
                context = event.context
            )
        }
    }

    private fun setFusedLocationProviderClient(context: Context) {
        _fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    }

    @SuppressLint("MissingPermission")
    private fun requestCurrentLocation() {
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                locationResult.lastLocation?.let {
                    val location = Location(
                        locality = "Meu Local",
                        latitude = it.latitude,
                        longitude = it.longitude,
                        isCurrentUserLocality = true
                    )
                    // Atualiza a localização no ViewModel
                    uiState.update { currentUiState ->
                        currentUiState.copy(
                            currentUserLocation = location,
                            userLocations = formUserLocationsList(
                                currentUserLocation = location,
                                userLocations = currentUiState.userLocations
                            ),
                            isLoadingLocations = false
                        )
                    }
                }
                // Remove as atualizações de localização após obter a primeira
                _fusedLocationClient?.removeLocationUpdates(this)
            }
        }

        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            1000
        ).build()

        _fusedLocationClient?.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun getUserLocations() {
        viewModelScope.launch {
            try {
                locationDao.getLocations()
                    .onEach { locations ->
                        Log.d(
                            "currentLocation",
                            "getUserLocations - try -> list daoLocations size: ${locations.size}"
                        )
                        uiState.update { currentUiState ->
                            currentUiState.copy(
                                userLocations = locations,
                            )
                        }
                    }.launchIn(this)

                requestCurrentLocation()

                Log.d(
                    "currentLocation",
                    "getUserLocations - try -> list userLocations size: ${uiState.value.userLocations.size}"
                )

                val locationsForecasts = mutableMapOf<Int?, ForecastDto?>()

                // fetching forecast data for current location
                uiState.value.userLocations.onEach { location ->
                    val forecastResult = getForecastForLocation(location = location)
                    forecastResult.onSuccess { forecastData ->
                        locationsForecasts[location.id] = forecastData
                    }
                    forecastResult.onFailure {
                        locationsForecasts[location.id] = null
                    }
                }

                uiState.update { currentUiState ->
                    currentUiState.copy(locationsForecasts = locationsForecasts)
                }

                Log.d(
                    "currentLocation",
                    "getUserLocations - try -> map locationsForecasts viewModel size: ${locationsForecasts.size}"
                )
                Log.d(
                    "currentLocation",
                    "getUserLocations - try -> map locationsForecasts size: ${uiState.value.locationsForecasts.size}"
                )
            } catch (e: Exception) {
                uiState.update {
                    it.copy(
                        userLocations = emptyList()
                    )
                }
                Log.e(
                    "currentLocation",
                    "Error when fetching user locations: ${e.printStackTrace()}"
                )
            }
        }
    }

    private suspend fun getForecastForLocation(location: Location): Result<ForecastDto> {
        return try {
            val response = openWeatherRepository.getCurrentForecast(
                lat = location.latitude,
                lng = location.latitude,
            )
            if (response.isSuccessful) {
                Log.i("currentForecast", "Success when fetching forecast: ${response.body()}")
                Result.success(response.body()!!)
            } else {
                Log.e(
                    "currentForecast",
                    "Error when fetching forecast: ${response.errorBody()}"
                )
                Result.failure(exception = Throwable(message = "Error when fetching forecast"))
            }
        } catch (e: Exception) {
            Log.e("currentForecast", "Exception -> error when fetching forecast: ${e.cause}")
            Result.failure(e)
        }
    }
}