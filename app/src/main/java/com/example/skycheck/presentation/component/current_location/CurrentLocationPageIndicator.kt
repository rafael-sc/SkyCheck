package com.example.skycheck.presentation.component.current_location

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.skycheck.R
import com.example.skycheck.presentation.theme.ColorPagerInactive
import com.example.skycheck.presentation.theme.ColorTextAction

@Composable
fun CurrentLocationPageIndicator(
    pagesSize: Int,
    selectedPage: Int
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            repeat(pagesSize) { page ->
                if (page == 0) {
                    Icon(
                        painter = painterResource(id = R.drawable.location),
                        contentDescription = null,
                        tint = if (selectedPage == 0) ColorTextAction else ColorPagerInactive,
                        modifier = Modifier.size(16.dp)
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(if (page == selectedPage) ColorTextAction else ColorPagerInactive)
                    )
                }
            }
        }
    }
}