package com.example.skycheck.presentation.screen.locations

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skycheck.data.model.dao.LocationDao
import com.example.skycheck.data.model.entity.Location
import com.example.skycheck.data.repository_impl.OpenWeatherRepositoryImpl
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
        onEvent(event = LocationsUiEvent.OnGetLocations)
        searchedLocationQuery.update { "" }
    }

    fun onEvent(event: LocationsUiEvent) {
        when (event) {
            is LocationsUiEvent.OnChangeLocation -> onChangeLocation(event.value)
            is LocationsUiEvent.OnDeleteLocation -> onDeleteLocation(event.location)
            LocationsUiEvent.OnGetLocations -> onGetLocations()
            is LocationsUiEvent.OnSaveLocation -> onSaveLocation(event.location)
        }
    }

    private fun onDeleteLocation(location: Location) {
        viewModelScope.launch {
            try {
                uiState.update { currentState ->
                    currentState.copy(
                        userLocations = currentState.userLocations?.minus(location)
                    )
                }
                locationDao.deleteLocation(location)
            } catch (e: Exception) {
                Log.e("deleteLocation", "Error when deleting location: ${e.printStackTrace()}")
            }
        }
    }

    private fun onGetLocations() {
        viewModelScope.launch {
            try {
                uiState.update {
                    it.copy(
                        userLocations = locationDao.getLocations()
                    )
                }
            } catch (e: Exception) {
                uiState.update {
                    it.copy(
                        userLocations = emptyList()
                    )
                }
                Log.e("deleteLocation", "Error when deleting location: ${e.printStackTrace()}")
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
                        userLocations = currentState.userLocations?.plus(location)
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