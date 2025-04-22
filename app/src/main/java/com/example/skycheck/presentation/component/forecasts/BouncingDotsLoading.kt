package com.example.skycheck.presentation.component.forecasts

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.skycheck.presentation.theme.ColorPagerInactive
import kotlinx.coroutines.delay

@Composable
fun BouncingDotsLoading() {
    val dotSize = 8.dp
    val dotSpacing = 4.dp
    val jumpHeight = 8.dp

    val delays = listOf(0L, 150L, 300L) // delay para cada ponto

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(dotSpacing),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .wrapContentSize()
        ) {
            delays.forEach { delayTime ->
                BouncingDot(delayMillis = delayTime, dotSize = dotSize, jumpHeight = jumpHeight)
            }
        }
    }
}

@Composable
fun BouncingDot(
    delayMillis: Long,
    dotSize: Dp,
    jumpHeight: Dp
) {
    val offsetY = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        delay(delayMillis)
        while (true) {
            offsetY.animateTo(
                targetValue = -jumpHeight.value,
                animationSpec = tween(durationMillis = 300, easing = LinearOutSlowInEasing)
            )
            offsetY.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 300, easing = FastOutLinearInEasing)
            )
        }
    }

    Box(
        modifier = Modifier
            .size(dotSize)
            .offset(y = offsetY.value.dp)
            .background(ColorPagerInactive, CircleShape)
    )
}
