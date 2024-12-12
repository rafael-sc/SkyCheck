package com.example.skycheck.presentation.component.current_location

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.skycheck.presentation.theme.ColorTextPrimary

@Composable
fun CurrentLocationBottomBar(
    pagesSize: Int,
    selectedPage: Int,
    onLocationsClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .height(52.dp)
            .padding(horizontal = 16.dp)
    ) {
        CurrentLocationPageIndicator(
            pagesSize = pagesSize,
            selectedPage = selectedPage
        )
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.CenterEnd
        ) {
            IconButton(
                onClick = { onLocationsClick() },
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.List,
                    contentDescription = "Localidades",
                    tint = ColorTextPrimary
                )
            }
        }
    }
}