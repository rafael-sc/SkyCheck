package com.example.skycheck.presentation.component.current_location

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.skycheck.R

@Composable
fun CurrentLocationSunriseAndSunset() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(30))
            .background(Color.White)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        CurrentLocationForecastItem(
            iconRes = R.drawable.sunrise,
            label = stringResource(id = R.string.nascer_do_sol),
            value = "05:07h"
        )
        CurrentLocationForecastItem(
            iconRes = R.drawable.sunset,
            label = stringResource(id = R.string.por_do_sol),
            value = "18:27h"
        )
    }
}