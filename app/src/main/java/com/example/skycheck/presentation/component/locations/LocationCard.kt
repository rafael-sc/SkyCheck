package com.example.skycheck.presentation.component.locations

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skycheck.R
import com.example.skycheck.data.model.dto.ForecastDto
import com.example.skycheck.data.model.entity.Location
import com.example.skycheck.data.model.mock.locationsPageMock
import com.example.skycheck.data.model.mock.mockForecast
import com.example.skycheck.presentation.theme.ColorTextPrimary
import com.example.skycheck.presentation.theme.ColorTextPrimaryVariant
import com.example.skycheck.presentation.theme.MaxTemperature
import com.example.skycheck.presentation.theme.MinTemperature
import com.example.skycheck.utils.capitalizeSentence
import com.example.skycheck.utils.convertKelvinToCelsius
import com.example.skycheck.utils.getImageOfForecast

@Composable
fun LocationCard(
    location: Location,
    forecast: ForecastDto?,
    isCurrentUserLocation: Boolean = false
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .shadow(
                elevation = 8.dp,
                spotColor = Color.Black.copy(alpha = 0.6f),
                ambientColor = Color.Gray,
                shape = RoundedCornerShape(16.dp),
                clip = true
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White)
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column {
                    Text(
                        text = if (isCurrentUserLocation) stringResource(id = R.string.meu_local)
                        else stringResource(id = R.string.localidade, location.locality),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = ColorTextPrimary
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    if (isCurrentUserLocation) {
                        Text(
                            text = stringResource(id = R.string.localidade, location.locality),
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Medium
                            ),
                            color = ColorTextPrimary
                        )
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier.size(52.dp),
                        painter = forecast?.weather?.get(0)?.icon?.let {
                            painterResource(
                                id = getImageOfForecast(
                                    it
                                )
                            )
                        } ?: painterResource(id = R.drawable.img_clouds),
                        contentDescription = null
                    )
                    Text(
                        text = forecast?.main?.temp?.let {
                            stringResource(
                                id = R.string.valor_temperatura,
                                convertKelvinToCelsius(temperature = it)
                            )
                        } ?: "--",
                        style = MaterialTheme.typography.headlineLarge.copy(fontSize = 44.sp)
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = forecast?.weather?.get(0)?.description?.let { desc ->
                        capitalizeSentence(sentence = desc)
                    } ?: "--",
                    style = MaterialTheme.typography.titleMedium,
                    color = ColorTextPrimaryVariant,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.width(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LocationsMinMaxForecast(
                        value = forecast?.main?.minTemperature?.let {
                            convertKelvinToCelsius(temperature = it)
                        } ?: 0,
                        icon = Icons.Default.ArrowDropDown,
                        color = MinTemperature
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    LocationsMinMaxForecast(
                        value = forecast?.main?.maxTemperature?.let {
                            convertKelvinToCelsius(temperature = it)
                        } ?: 0,
                        icon = Icons.Default.ArrowDropUp,
                        color = MaxTemperature
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun LocationCardPreview() {
    LocationCard(
        location = locationsPageMock[0],
        forecast = mockForecast,
    )
}