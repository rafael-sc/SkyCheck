package com.example.skycheck.presentation.screen.current_location

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.skycheck.data.model.mock.locationsPageMock
import com.example.skycheck.presentation.component.current_location.CurrentLocationBottomBar
import com.example.skycheck.presentation.component.current_location.CurrentLocationPage
import com.example.skycheck.presentation.route.Locations
import com.example.skycheck.presentation.theme.ColorBackground
import org.koin.androidx.compose.koinViewModel

@Composable
fun CurrentLocationScreen(
    navController: NavController,
    viewModel: CurrentLocationViewModel = koinViewModel()
) {

    val pagerState = rememberPagerState(initialPage = 0) { locationsPageMock.size }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = ColorBackground,
        bottomBar = {
            CurrentLocationBottomBar(
                pagesSize = locationsPageMock.size,
                selectedPage = pagerState.currentPage,
                onLocationsClick = {
                    navController.navigate(Locations)
                }
            )
        }
    ) { innerPadding ->
        HorizontalPager(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            state = pagerState
        ) {
            CurrentLocationPage(
                locationPage = locationsPageMock[pagerState.currentPage],
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun CurrentLocationScreenPreview() {
    CurrentLocationScreen(navController = rememberNavController())
}