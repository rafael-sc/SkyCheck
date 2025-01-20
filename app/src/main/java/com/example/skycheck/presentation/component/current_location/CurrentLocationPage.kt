package com.example.skycheck.presentation.component.current_location

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.skycheck.data.model.dto.ForecastDto
import com.example.skycheck.data.model.entity.Location
import com.example.skycheck.presentation.screen.current_location.MainImageForecastDimensions

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CurrentLocationPage(
    location: Location,
    forecastData: ForecastDto,
    currentMainImage: MainImageForecastDimensions
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
    ) {
        CurrentLocationHeaderLocality(locality = location.locality)
        Spacer(modifier = Modifier.height(28.dp))
        CurrentLocationMainCardForecast(
            forecastData = forecastData,
            currentMainImage = currentMainImage
        )
        Spacer(modifier = Modifier.height(20.dp))
        CurrentLocationSunriseAndSunset(forecastData = forecastData)
        Spacer(modifier = Modifier.height(20.dp))
        CurrentLocationAirInfo(modifier = Modifier.weight(1f), forecastData = forecastData)
        Spacer(modifier = Modifier.height(20.dp))
        CurrentLocationWeekForecastList()
    }
}