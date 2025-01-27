package com.example.skycheck.presentation.screen.locations

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
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LocationsViewModel(
    private val openWeatherRepository: OpenWeatherRepositoryImpl,
    private val locationDao: LocationDao
) : ViewModel() {
    val uiState = MutableStateFlow(LocationsUiState())
    val searchedLocationQuery = MutableStateFlow("")
    private var _fusedLocationClient: FusedLocationProviderClient? = null

    @OptIn(FlowPreview::class)
    val geocodeLocations = searchedLocationQuery
        .debounce(700)
        .filter { it.isNotEmpty() }
        .distinctUntilChanged() // Ignore valores iguais consecutivos
        .onEach { query ->
            searchLocations(query)
        }
        .launchIn(viewModelScope)

    init {
        searchedLocationQuery.update { "" }
    }

    fun onEvent(event: LocationsUiEvent) {
        when (event) {
            is LocationsUiEvent.OnChangeLocation -> onChangeLocation(event.value)
            is LocationsUiEvent.OnDeleteLocation -> onDeleteLocation(event.location)
            LocationsUiEvent.OnGetLocations -> onGetLocations()
            is LocationsUiEvent.OnSaveLocation -> onSaveLocation(event.location)
            LocationsUiEvent.OnRequestCurrentLocation -> requestCurrentLocation()
            is LocationsUiEvent.OnSetFusedLocationProviderClient -> setFusedLocationProviderClient(
                event.context
            )
        }
    }

    private fun setFusedLocationProviderClient(context: Context) {
        _fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    }

    private fun onDeleteLocation(location: Location) {
        viewModelScope.launch {
            try {
                Log.d("locations", "Current locations: ${uiState.value.userLocations}")
//                uiState.update { currentState ->
//                    currentState.copy(
//                        userLocations = currentState.userLocations.minus(location)
//                    )
//                }
                locationDao.deleteLocation(location)
                Log.d("locations", "Locations after update: ${uiState.value.userLocations}")

            } catch (e: Exception) {
                Log.e("deleteLocation", "Error when deleting location: ${e.printStackTrace()}")
            }
        }
    }

    private fun onGetLocations() {
        viewModelScope.launch {
            try {
                locationDao.getLocations()
                    .onEach { locations ->
                        uiState.update { currentUiState ->
                            currentUiState.copy(
                                userLocations = locations,
                            )
                        }
                    }.launchIn(this)
                requestCurrentLocation()
            } catch (e: Exception) {
                uiState.update {
                    it.copy(
                        userLocations = emptyList(),
                    )
                }
                Log.e(
                    "currentLocation",
                    "Error when fetching user locations: ${e.printStackTrace()}"
                )
            }
        }
    }

    private fun onSaveLocation(location: Location) {
        viewModelScope.launch {
            try {
                locationDao.saveLocation(location = location)
                Log.d("locations", "Success when saving Location in Database")
                uiState.update { currentState ->
                    currentState.copy(
                        geocodeLocations = emptyList(),
                        userLocations = currentState.userLocations.plus(location)
                    )
                }
                searchedLocationQuery.update { "" }
            } catch (e: Exception) {
                Log.e("locations", "Error when saving Location in Database: ${e.printStackTrace()}")
            }
        }
    }

    private fun onChangeLocation(value: String) {
        searchedLocationQuery.update { value }
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
                                userLocations = currentUiState.userLocations,
                            ),
                            isLoadingLocations = false,
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

    private fun searchLocations(value: String) {
        viewModelScope.launch {
            uiState.update { it.copy(isSearching = true) }
            try {
                val response = openWeatherRepository.getGeocodeFromText(query = value)
                if (response.isSuccessful) {
                    Log.e("searchLocation", "Success when searching locations: ${response.body()}")
                    uiState.update {
                        it.copy(geocodeLocations = response.body() ?: emptyList())
                    }
                } else {
                    Log.e(
                        "searchLocation",
                        "Error when searching locations: ${response.errorBody()}"
                    )
                    uiState.update { it.copy(geocodeLocations = emptyList()) }
                }
            } catch (e: Exception) {
                Log.e("searchLocation", "Exception -> error when searching locations: ${e.message}")
                uiState.update { it.copy(geocodeLocations = emptyList()) }
            } finally {
                uiState.update { it.copy(isSearching = false) }
            }
        }
    }
}