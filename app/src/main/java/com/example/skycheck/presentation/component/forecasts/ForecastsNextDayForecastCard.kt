package com.example.skycheck.presentation.component.forecasts

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.skycheck.R
import com.example.skycheck.data.model.dto.ForecastDto
import com.example.skycheck.presentation.theme.ColorTextPrimary
import com.example.skycheck.presentation.theme.ColorTextPrimaryVariant
import com.example.skycheck.presentation.theme.ColorTextSecondary
import com.example.skycheck.presentation.theme.MaxTemperature
import com.example.skycheck.presentation.theme.MinTemperature
import com.example.skycheck.utils.convertKelvinToCelsius
import com.example.skycheck.utils.formatCurrentDate
import com.example.skycheck.utils.getDayOfWeek
import com.example.skycheck.utils.getImageOfForecast
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ForecastsNextDayForecastCard(
    forecast: ForecastDto,
    daysBefore: Int
) {
    val dayOfWeek = getDayOfWeek(date = LocalDate.now().plusDays(daysBefore.toLong()))
    val formattedDate = formatCurrentDate(date = LocalDate.now().plusDays(daysBefore.toLong()))

    Column(
        modifier = Modifier
            .width(68.dp)
            .height(180.dp)
            .clip(RoundedCornerShape(80))
            .background(ColorTextSecondary)
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.data, dayOfWeek),
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                color = ColorTextPrimary
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = stringResource(id = R.string.data, formattedDate),
                style = MaterialTheme.typography.labelSmall,
                color = ColorTextPrimaryVariant
            )
        }
        Image(
            painter = painterResource(id = getImageOfForecast(forecast.weather[0].icon)),
            contentDescription = null
        )
        Column {
            ForecastsRowMaxMinTemp(
                icon = Icons.Default.ArrowDropUp,
                color = MaxTemperature,
                value = convertKelvinToCelsius(forecast.main.maxTemperature)
            )
            ForecastsRowMaxMinTemp(
                icon = Icons.Default.ArrowDropDown,
                color = MinTemperature,
                value = convertKelvinToCelsius(forecast.main.minTemperature)
            )
        }
    }
}