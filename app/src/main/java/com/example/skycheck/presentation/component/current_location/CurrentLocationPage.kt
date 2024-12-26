package com.example.skycheck.presentation.component.current_location

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.skycheck.data.model.entity.Location

@Composable
fun CurrentLocationPage(
    locationPage: Location
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
    ) {
        CurrentLocationHeaderLocality(locality = locationPage.locality)
        Spacer(modifier = Modifier.height(28.dp))
        CurrentLocationMainCardForecast()
        Spacer(modifier = Modifier.height(20.dp))
        CurrentLocationSunriseAndSunset()
        Spacer(modifier = Modifier.height(20.dp))
        CurrentLocationAirInfo(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.height(20.dp))
        CurrentLocationWeekForecastList()
    }
}