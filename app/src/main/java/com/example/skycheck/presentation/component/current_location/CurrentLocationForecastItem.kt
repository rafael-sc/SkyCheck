package com.example.skycheck.presentation.component.current_location

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.skycheck.presentation.theme.ColorTextAction
import com.example.skycheck.presentation.theme.ColorTextPrimary
import com.example.skycheck.presentation.theme.ColorTextPrimaryVariant

@Composable
fun CurrentLocationForecastItem(
    modifier: Modifier = Modifier,
    @DrawableRes iconRes: Int,
    label: String,
    value: String
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = label,
            tint = ColorTextAction,
            modifier = Modifier.size(28.dp)
        )
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = label,
                color = ColorTextPrimaryVariant,
                style = MaterialTheme.typography.labelMedium,
            )
            Text(
                text = value,
                color = ColorTextPrimary,
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
            )
        }
    }
}