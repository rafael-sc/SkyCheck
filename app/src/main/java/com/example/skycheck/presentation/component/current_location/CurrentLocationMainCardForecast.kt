package com.example.skycheck.presentation.component.current_location

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.skycheck.R
import com.example.skycheck.presentation.theme.ColorGradient1
import com.example.skycheck.presentation.theme.ColorGradient2
import com.example.skycheck.presentation.theme.ColorGradient3
import com.example.skycheck.presentation.theme.ColorTextSecondary
import com.example.skycheck.presentation.theme.ColorTextSecondaryVariant

@Composable
fun CurrentLocationMainCardForecast() {
    Box(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(15))
                .background(
                    brush = Brush.horizontalGradient(
                        0f to ColorGradient1,
                        0.5f to ColorGradient2,
                        1f to ColorGradient3,
                    )
                )
                .padding(vertical = 12.dp, horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                verticalArrangement = Arrangement.Bottom
            ) {
                Column {
                    Text(
                        text = stringResource(id = R.string.data, "Parcialmente nublado"),
                        color = ColorTextSecondary,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = stringResource(id = R.string.data, "Terça, 10 Dez"),
                        color = ColorTextSecondaryVariant,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold)
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.6f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.valor_temperatura, 21),
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 72.sp,
                        brush = Brush.linearGradient(
                            0f to Color.White,
                            1f to Color.White.copy(alpha = 0.3f)
                        )
                    ),
                    modifier = Modifier.height(72.dp)
                )
                Text(
                    text = stringResource(id = R.string.sensacao_termica, 23),
                    color = ColorTextSecondaryVariant,
                    style = MaterialTheme.typography.titleSmall
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    CurrentLocationColumnMaxMinTemp(icon = Icons.Default.ArrowDropDown, value = 16)
                    CurrentLocationColumnMaxMinTemp(icon = Icons.Default.ArrowDropUp, value = 25)
                }
            }
        }
        Box(modifier = Modifier.padding(horizontal = 20.dp)) {
            Image(
                painter = painterResource(id = R.drawable.img_thunder),
                contentDescription = null,
                modifier = Modifier
                    .size(152.dp)
                    .offset(y = (-36).dp)
                    .zIndex(3f)
            )
        }
    }
}