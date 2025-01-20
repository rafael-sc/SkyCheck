package com.example.skycheck.presentation.component.current_location

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.skycheck.R
import com.example.skycheck.data.model.dto.ForecastDto
import com.example.skycheck.utils.formatTimestampToTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CurrentLocationSunriseAndSunset(forecastData: ForecastDto) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(30))
            .background(Color.White)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        CurrentLocationForecastItem(
            iconRes = R.drawable.sunrise,
            label = stringResource(id = R.string.nascer_do_sol),
            value = stringResource(id = R.string.hora, formatTimestampToTime(forecastData.sys.sunrise))
        )
        Spacer(Modifier.width(20.dp))
        Image(
            painter = painterResource(id = R.drawable.img_sun),
            contentDescription = "Sol",
            modifier = Modifier.size(52.dp)
        )
        Spacer(Modifier.width(20.dp))
        CurrentLocationForecastItem(
            iconRes = R.drawable.sunset,
            label = stringResource(id = R.string.por_do_sol),
            value = stringResource(id = R.string.hora, formatTimestampToTime(forecastData.sys.sunset))
        )
    }
}