package com.example.skycheck.presentation.component.current_location

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.skycheck.R
import com.example.skycheck.presentation.theme.ColorTextSecondaryVariant

@Composable
fun CurrentLocationColumnMaxMinTemp(
    icon: ImageVector,
    value: Int
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = stringResource(id = R.string.valor_temperatura, value),
            color = ColorTextSecondaryVariant,
            style = MaterialTheme.typography.headlineMedium.copy(
                brush = Brush.linearGradient(
                    0f to Color.White,
                    1f to Color.White.copy(alpha = 0.3f)
                )
            ),
            modifier = Modifier.height(32.dp)
        )
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = ColorTextSecondaryVariant,
            modifier = Modifier.size(28.dp)
        )
    }
}