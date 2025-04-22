package com.example.skycheck.presentation.screen.forecasts

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skycheck.data.model.dto.ForecastDto
import com.example.skycheck.data.model.dto.Next5DaysForecastDto
import com.example.skycheck.data.model.entity.Location
import com.example.skycheck.data.repository_impl.LocationRepositoryImpl
import com.example.skycheck.data.repository_impl.OpenWeatherRepositoryImpl
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ForecastsViewModel(
    private val openWeatherRepository: OpenWeatherRepositoryImpl,
    private val locationRepository: LocationRepositoryImpl
) : ViewModel() {

    val uiState = MutableStateFlow(ForecastsUiState())

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
        val locationsNextDaysForecasts = mutableMapOf<Int?, List<ForecastDto>?>()

        // fetching forecast data for current location
        locations.onEach { location ->
            // current forecast
            val forecastResult = location?.let { getForecastForLocation(location = it) }
            forecastResult?.onSuccess { forecastData ->
                locationsForecasts[location.id] = forecastData
            }
            forecastResult?.onFailure {
                locationsForecasts[location.id] = null
            }

            // next days forecasts
            val nextDaysForecastResult =
                location?.let { getNextDaysForecastForLocation(location = it) }
            nextDaysForecastResult?.onSuccess { forecastData ->
                val forecastList = mutableListOf<ForecastDto>()
                val indexes = listOf(8, 16, 24, 32, 40)

                for (i in 0..forecastData.list.size) {
                    if (indexes.contains(i + 1)) {
                        forecastList.add(forecastData.list[i])
                    }
                }

                locationsNextDaysForecasts[location.id] = forecastList
            }
            nextDaysForecastResult?.onFailure {
                locationsNextDaysForecasts[location.id] = null
            }

        }

        uiState.update { currentUiState ->
            currentUiState.copy(
                userLocations = locations,
                locationsForecasts = locationsForecasts,
                locationsNextDaysForecasts = locationsNextDaysForecasts
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

    private suspend fun getNextDaysForecastForLocation(location: Location): Result<Next5DaysForecastDto> {
        return try {
            val response = openWeatherRepository.getNext5DaysForecast(
                lat = location.latitude,
                lng = location.latitude,
            )
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(exception = Throwable(message = "Error when fetching next days forecasts"))
            }
        } catch (e: Exception) {
            Log.e(
                "currentForecast",
                "Exception -> error when fetching next days forecasts: ${e.cause}"
            )
            Result.failure(e)
        }
    }
}