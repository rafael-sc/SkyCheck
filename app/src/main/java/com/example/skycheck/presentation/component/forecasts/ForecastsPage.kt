package com.example.skycheck.presentation.component.forecasts

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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ForecastsPage(
    location: Location,
    forecastData: ForecastDto?,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
    ) {
        ForecastsHeaderLocality(locality = location.locality)
        Spacer(modifier = Modifier.height(28.dp))
        ForecastsMainCardForecast(forecastData = forecastData)
        Spacer(modifier = Modifier.height(20.dp))
        ForecastsSunriseAndSunset(forecastData = forecastData)
        Spacer(modifier = Modifier.height(20.dp))
        ForecastsAirInfo(modifier = Modifier.weight(1f), forecastData = forecastData)
        Spacer(modifier = Modifier.height(20.dp))
        ForecastsWeekForecastList()
    }
}