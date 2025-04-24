package com.example.skycheck.presentation.screen.locations

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skycheck.data.model.dto.ForecastDto
import com.example.skycheck.data.model.entity.Location
import com.example.skycheck.data.repository_impl.LocationRepositoryImpl
import com.example.skycheck.data.repository_impl.OpenWeatherRepositoryImpl
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.async
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
    private val locationRepository: LocationRepositoryImpl
) : ViewModel() {
    val uiState = MutableStateFlow(LocationsUiState())
    val searchedLocationQuery = MutableStateFlow("")

    init {
        handleLocationsAndForecast()
    }

    private fun handleLocationsAndForecast() {
        viewModelScope.launch {
            val currentLocationDeferred = async { locationRepository.getCurrentLocation() }
            val savedLocationsDeferred = async { locationRepository.getSavedLocations() }

            val currentLocation = currentLocationDeferred.await()
            val savedLocations = savedLocationsDeferred.await()

            val userLocations = listOfNotNull(currentLocation) + savedLocations

            handleMapForecastLocations(locations = userLocations)

            uiState.update { currentUiState ->
                currentUiState.copy(
                    isLoadingLocations = false
                )
            }
        }
    }

    private suspend fun handleMapForecastLocations(locations: List<Location?>) {
        val locationsForecasts = mutableMapOf<Int?, ForecastDto?>()

        // fetching forecast data for current location
        locations.onEach { location ->
            val forecastResult = location?.let { getForecastForLocation(location = it) }
            forecastResult?.onSuccess { forecastData ->
                locationsForecasts[location.id] = forecastData
            }
            forecastResult?.onFailure {
                locationsForecasts[location.id] = null
            }
        }

        uiState.update { currentUiState ->
            currentUiState.copy(
                locations = locations,
                locationsForecasts = locationsForecasts
            )
        }
    }

    private suspend fun getForecastForLocation(location: Location): Result<ForecastDto> {
        return try {
            val response = openWeatherRepository.getCurrentForecast(
                lat = location.latitude,
                lng = location.latitude,
            )
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(exception = Throwable(message = "Error when fetching forecast"))
            }
        } catch (e: Exception) {
            Log.e("currentForecast", "Exception -> error when fetching forecast: ${e.cause}")
            Result.failure(e)
        }
    }

    fun deleteLocation(location: Location) {
        viewModelScope.launch {
            try {
                locationRepository.deleteLocation(location = location)

                val updatedLocationForecasts = mutableMapOf<Int?, ForecastDto?>()
                updatedLocationForecasts.putAll(uiState.value.locationsForecasts)
                updatedLocationForecasts.remove(location.id)

                uiState.update { currentState ->
                    currentState.copy(
                        locations = currentState.locations.minus(location),
                        locationsForecasts = updatedLocationForecasts
                    )
                }
            } catch (e: Exception) {
                Log.e("deleteLocation", "Error when deleting location: ${e.printStackTrace()}")
            }
        }
    }

    fun saveLocation(location: Location) {
        viewModelScope.launch {
            try {
                val savedLocationId = locationRepository.saveLocation(location = location).toInt()
                val savedLocation = mapLocation(newId = savedLocationId, location = location)

                val forecastForLocation = getForecastForLocation(location = location)

                val updatedLocationForecasts = mutableMapOf<Int?, ForecastDto?>()
                updatedLocationForecasts.putAll(uiState.value.locationsForecasts)

                forecastForLocation.onSuccess { forecastData ->
                    updatedLocationForecasts[savedLocationId] = forecastData
                }
                forecastForLocation.onFailure {
                    updatedLocationForecasts[savedLocationId] = null
                }

                uiState.update { currentState ->
                    currentState.copy(
                        geocodeLocations = emptyList(),
                        locations = currentState.locations.plus(savedLocation),
                        locationsForecasts = updatedLocationForecasts
                    )
                }
                searchedLocationQuery.update { "" }
            } catch (e: Exception) {
                Log.e("locations", "Error when saving Location in Database: ${e.printStackTrace()}")
            }
        }
    }

    private fun mapLocation(newId: Int, location: Location): Location {
        return Location(
            id = newId,
            locality = location.locality,
            latitude = location.latitude,
            longitude = location.longitude,
            isCurrentUserLocality = location.isCurrentUserLocality,
        )
    }

    @OptIn(FlowPreview::class)
    fun onChangeLocation(value: String) {
        searchedLocationQuery.update { value }

        searchedLocationQuery
            .debounce(700)
            .filter { it.isNotEmpty() }
            .distinctUntilChanged() // Ignore valores iguais consecutivos
            .onEach { query ->
                searchLocations(query)
            }
            .launchIn(viewModelScope)
    }

    private fun searchLocations(value: String) {
        viewModelScope.launch {
            uiState.update { it.copy(isSearching = true) }
            try {
                val response = openWeatherRepository.getGeocodeFromText(query = value)
                if (response.isSuccessful) {
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