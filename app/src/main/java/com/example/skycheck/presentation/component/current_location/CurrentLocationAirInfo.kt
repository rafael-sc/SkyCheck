package com.example.skycheck.presentation.component.current_location

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
fun CurrentLocationAirInfo(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(25))
            .background(Color.White)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        CurrentLocationForecastItem(
            iconRes = R.drawable.ic_wind,
            label = stringResource(id = R.string.vento),
            value = "9km/h"
        )
        Spacer(modifier = Modifier.width(24.dp))
        CurrentLocationForecastItem(
            iconRes = R.drawable.ic_so2,
            label = stringResource(id = R.string.umidade),
            value = "35%"
        )
        Spacer(modifier = Modifier.width(24.dp))
        CurrentLocationForecastItem(
            iconRes = R.drawable.ic_air_quality_header,
            label = stringResource(id = R.string.nuvens),
            value = "75%"
        )
    }
}