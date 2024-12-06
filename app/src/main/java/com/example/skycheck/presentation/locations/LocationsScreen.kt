package com.example.skycheck.presentation.locations

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skycheck.R
import com.example.skycheck.ui.ColorBackground
import com.example.skycheck.ui.ColorTextAction
import com.example.skycheck.ui.ColorTextPrimary
import com.example.skycheck.ui.ColorTextPrimaryVariant
import com.example.skycheck.ui.MaxTemperature
import com.example.skycheck.ui.MinTemperature
import com.example.skycheck.ui.PurpleGrey80

@Composable
fun LocationsScreen() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = ColorBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(
                    start = 24.dp,
                    end = 24.dp,
                    top = 20.dp
                )
        ) {
            Text(
                text = "Localidades",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                color = ColorTextPrimary
            )
            Spacer(modifier = Modifier.height(16.dp))
            SearchInput(value = "") {}
            Spacer(modifier = Modifier.height(16.dp))
            LocationCard()
        }
    }
}

@Composable
private fun SearchInput(
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        placeholder = {
            Text(
                text = stringResource(id = R.string.buscar_localidade),
                style = MaterialTheme.typography.bodyLarge
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            disabledContainerColor = Color.White,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedTextColor = ColorTextAction,
            unfocusedTextColor = PurpleGrey80,
            disabledPlaceholderColor = PurpleGrey80,
            cursorColor = ColorTextAction,
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.White,
            unfocusedPlaceholderColor = ColorTextPrimaryVariant
        ),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                spotColor = Color.Black.copy(alpha = 0.6f),
                ambientColor = Color.Gray,
                shape = RoundedCornerShape(16.dp),
                clip = true
            ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = ColorTextPrimaryVariant
            )
        }
    )
}

@Composable
private fun LocationCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
//                .background(
//                    brush = Brush.linearGradient(
//                        colors = listOf(Color(0xFF4C7070), Color(0xFF8D8D8D)), // Cinza carvão para preto
//                        start = Offset(0f, 0f),
//                        end = Offset(0f, Float.POSITIVE_INFINITY)
//                    )
//                    Brush.linearGradient(
//                        colors = listOf(Color(0xFFFFE34A), Color(0xFFFFA333)), // Amarelo vibrante para laranja
//                        start = Offset(0f, 0f),
//                        end = Offset(0f, Float.POSITIVE_INFINITY)
//                    )
//                    Brush.linearGradient(
//                        colors = listOf(Color(0xFFFFFFFF), Color(0xFFB0E0E6)), // Branco para azul pálido
//                        start = Offset(0f, 0f),
//                        end = Offset(0f, Float.POSITIVE_INFINITY)
//                    )
//                )
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
                        text = stringResource(id = R.string.meu_local),
                        style = MaterialTheme.typography.titleLarge.copy(fontSize = 24.sp, fontWeight = FontWeight.SemiBold),
                        color = ColorTextPrimary
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = stringResource(id = R.string.localidade, "São Paulo"),
                        style = MaterialTheme.typography.titleLarge.copy(fontSize = 20.sp, fontWeight = FontWeight.Medium),
                        color = ColorTextPrimary
                    )
                }
                Text(
                    text = stringResource(id = R.string.valor_temperatura, 27),
                    style = MaterialTheme.typography.headlineLarge.copy(fontSize = 44.sp)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "Tempestade com chuva leve",
                    style = MaterialTheme.typography.labelMedium,
                    color = ColorTextPrimaryVariant
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    MinMaxForecast(
                        value = "21",
                        icon = Icons.Default.ArrowDropDown,
                        color = MaxTemperature
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    MinMaxForecast(
                        value = "33",
                        icon = Icons.Default.ArrowDropUp,
                        color = MinTemperature
                    )
                }
            }
        }
    }
}

@Composable
private fun MinMaxForecast(
    value: String,
    icon: ImageVector,
    color: Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = color
        )
        Text(
            text = stringResource(id = R.string.valor_temperatura, value.toInt()),
            style = MaterialTheme.typography.labelMedium,
            color = ColorTextPrimaryVariant
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun LocationsScreenPreview() {
    LocationsScreen()
}