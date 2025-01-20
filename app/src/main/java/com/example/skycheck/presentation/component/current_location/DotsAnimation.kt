package com.example.skycheck.presentation.component.current_location

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.skycheck.presentation.theme.ColorPagerInactive
import kotlinx.coroutines.delay

@Composable
fun DotsAnimation() {
    val dotCount = 3
    val dotSpacing = 8.dp
    val durationMillis = 300

    // Create a list of animations for each dot
    val scales = List(dotCount) { Animatable(1f) }

    LaunchedEffect(Unit) {
        while (true) {
            for (i in scales.indices) {
                scales[i].animateTo(
                    targetValue = 1.5f,
                    animationSpec = tween(durationMillis)
                )
                delay(durationMillis.toLong())
                scales[i].animateTo(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis)
                )

            }
        }
    }

    Row(
        horizontalArrangement = Arrangement.spacedBy(dotSpacing),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(16.dp)
    ) {
        scales.forEach { scale ->
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .scale(scale.value)
                    .clip(CircleShape)
                    .background(ColorPagerInactive)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DotsAnimationPreview() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        DotsAnimation()
    }
}