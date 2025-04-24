package com.example.skycheck.presentation.component.locations

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import com.example.skycheck.data.model.entity.Location
import com.example.skycheck.presentation.theme.RedDelete
import kotlinx.coroutines.delay
import kotlin.math.abs

@Composable
fun SwipeToDeleteContainer(
    location: Location,
    onDelete: (Location) -> Unit,
    animationDuration: Int = 500,
    content: @Composable () -> Unit,
) {
    var isRemoved by remember { mutableStateOf(false) }
    val hapticFeedback = LocalHapticFeedback.current

    val state = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if (value == SwipeToDismissBoxValue.EndToStart) {
                isRemoved = true
                true
            } else {
                false
            }
        },
        positionalThreshold = { fullWidth ->
            fullWidth * 0.30f
        },
    )

    var hapticTriggered by remember { mutableStateOf(false) }
    var fullWidth by remember { mutableFloatStateOf(1f) }

    LaunchedEffect(state) {
        snapshotFlow { state.requireOffset() }
            .collect { offset ->
                val fraction = abs(offset) / fullWidth
                if (fraction >= 0.30f && !hapticTriggered) {
                    hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                    hapticTriggered = true
                } else if (fraction < 0.30f) {
                    hapticTriggered = false
                }
            }
    }

    LaunchedEffect(key1 = isRemoved) {
        if (isRemoved) {
            delay(animationDuration.toLong())
            onDelete(location)
        }
    }

    AnimatedVisibility(
        visible = !isRemoved,
        exit = shrinkVertically(
            animationSpec = tween(durationMillis = animationDuration),
            shrinkTowards = Alignment.Top
        ) + fadeOut()
    ) {
        Box(modifier = Modifier.onSizeChanged { size ->
            fullWidth = size.width.toFloat()
        }) {
            SwipeToDismissBox(
                state = state,
                backgroundContent = {
                    val color =
                        if (state.dismissDirection == SwipeToDismissBoxValue.EndToStart) RedDelete
                        else Color.Transparent

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(16.dp))
                            .background(color)
                            .padding(16.dp),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Remover",
                            modifier = Modifier,
                            tint = Color.White
                        )
                    }
                },
                content = { content() },
                enableDismissFromEndToStart = true,
                enableDismissFromStartToEnd = false
            )
        }
    }
}