package com.example.skycheck.presentation.component.forecasts

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skycheck.R
import com.example.skycheck.data.model.dto.ForecastDto
import com.example.skycheck.presentation.theme.ColorTextPrimary

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ForecastsWeekForecastList(forecastList: List<ForecastDto>?) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(id = R.string.proximos_dias),
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            ),
            color = ColorTextPrimary
        )
        Spacer(modifier = Modifier.height(8.dp))
        if (!forecastList.isNullOrEmpty()) {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                items(items = forecastList) { forecastData ->
                    ForecastsNextDayForecastCard(
                        forecast = forecastData,
                        daysBefore = forecastList.indexOf(forecastData) + 1
                    )
                }
            }
        }
    }
}