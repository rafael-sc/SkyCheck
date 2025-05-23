package com.example.skycheck.presentation.screen.locations

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.skycheck.R
import com.example.skycheck.data.model.entity.Location
import com.example.skycheck.presentation.component.locations.LocationCard
import com.example.skycheck.presentation.component.locations.SearchInput
import com.example.skycheck.presentation.component.locations.SwipeToDeleteContainer
import com.example.skycheck.presentation.route.UiRoutes
import com.example.skycheck.presentation.theme.ColorBackground
import com.example.skycheck.presentation.theme.ColorTextAction
import com.example.skycheck.presentation.theme.ColorTextPrimary
import com.example.skycheck.utils.LoadingBox
import org.koin.androidx.compose.koinViewModel

@Composable
fun LocationsScreen(
    navController: NavController,
    viewModel: LocationsViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val searchedLocationQuery by viewModel.searchedLocationQuery.collectAsState()

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
                    top = 12.dp
                )
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart) {
                    IconButton(
                        onClick = {
                            navController.navigate(
                                UiRoutes.Forecasts(needReload = uiState.hasChanged)
                            )
                        },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            modifier = Modifier.fillMaxSize(),
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "Voltar",
                            tint = ColorTextAction
                        )
                    }
                }
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text(
                        text = stringResource(id = R.string.localidades),
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                        color = ColorTextPrimary
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            SearchInput(
                value = searchedLocationQuery,
                onValueChange = { newValue ->
                    viewModel.onChangeLocation(value = newValue)
                },
                isSearching = uiState.isSearching
            )
            Spacer(modifier = Modifier.height(4.dp))
            AnimatedVisibility(
                visible = uiState.geocodeLocations.isNotEmpty()
            ) {
                Box {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.White)
                            .padding(8.dp)
                    ) {
                        items(items = uiState.geocodeLocations) { location ->
                            Text(
                                text = stringResource(
                                    id = R.string.localidade_estado_pais,
                                    location.name,
                                    location.state,
                                    location.country
                                ),
                                style = MaterialTheme.typography.titleMedium,
                                color = ColorTextPrimary,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        viewModel.saveLocation(
                                            location = Location(
                                                locality = location.name,
                                                latitude = location.lat,
                                                longitude = location.lon,
                                                isCurrentUserLocality = false
                                            )
                                        )
                                    }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (uiState.isLoadingLocations) {
                LoadingBox()
            } else {
                if (uiState.locations.isNotEmpty() && uiState.locationsForecasts.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(
                            items = uiState.locations,
                            key = { it?.id ?: 0 }
                        ) { location ->
                            if (location != null) {
                                if (location.isCurrentUserLocality) {
                                    LocationCard(
                                        location = location,
                                        forecast = uiState.locationsForecasts[location.id]
                                    )
                                } else {
                                    SwipeToDeleteContainer(
                                        location = location,
                                        onDelete = {
                                            viewModel.deleteLocation(location = location)
                                        },
                                        content = {
                                            LocationCard(
                                                location = location,
                                                forecast = uiState.locationsForecasts[location.id],
                                            )
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}


@Preview(showSystemUi = true)
@Composable
private fun LocationsScreenPreview() {
    LocationsScreen(navController = rememberNavController())
}