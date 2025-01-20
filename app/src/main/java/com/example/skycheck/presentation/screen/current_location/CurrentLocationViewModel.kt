package com.example.skycheck.presentation.screen.current_location

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skycheck.data.model.dao.LocationDao
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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CurrentLocationViewModel(
    private val openWeatherRepository: OpenWeatherRepositoryImpl,
    private val locationDao: LocationDao
) : ViewModel() {

    val uiState = MutableStateFlow(CurrentLocationUiState())
    private var _fusedLocationClient: FusedLocationProviderClient? = null

    init {
        getUserLocations()
    }

    fun onEvent(event: CurrentLocationUiEvent) {
        when (event) {
            is CurrentLocationUiEvent.OnGetCurrentForecast -> getForecastForCurrentLocation(location = event.location)
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
                            )
                        )
                    }
                    getForecastForCurrentLocation(location = location)
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
        Log.d("loading", "Start getting user locations")
        viewModelScope.launch {
            try {
                locationDao.getLocations().collect { locations ->
                    uiState.update {
                        it.copy(userLocations = locations)
                    }
                }
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
            } finally {
                Log.d("loading", "Stop loading locations")
                uiState.update { it.copy(isLoadingLocations = false) }
            }
        }
    }

    private fun getForecastForCurrentLocation(location: Location) {
        viewModelScope.launch {
            uiState.update { it.copy(isFetchingForecast = true) }
            try {
                val response = openWeatherRepository.getCurrentForecast(
                    lat = location.latitude,
                    lng = location.latitude,
                )
                if (response.isSuccessful) {
                    Log.i("currentForecast", "Success when fetching forecast: ${response.body()}")
                    uiState.update { currentState ->
                        currentState.copy(
                            currentForecastData = response.body(),
                            currentMainImageForecast = getImageOfForecast(
                                openWeatherIconId = response.body()?.weather?.get(0)?.icon ?: ""
                            )
                        )
                    }
                } else {
                    Log.e(
                        "currentForecast",
                        "Error when fetching forecast: ${response.errorBody()}"
                    )
                }
            } catch (e: Exception) {
                Log.e("currentForecast", "Exception -> error when fetching forecast: ${e.cause}")
            } finally {
                uiState.update { it.copy(isFetchingForecast = false) }
            }
        }
    }
}