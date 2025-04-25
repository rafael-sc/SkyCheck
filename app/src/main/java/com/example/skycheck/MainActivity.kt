package com.example.skycheck

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.Ease
import androidx.compose.animation.core.tween
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.skycheck.presentation.route.UiRoutes
import com.example.skycheck.presentation.screen.forecasts.ForecastsScreen
import com.example.skycheck.presentation.screen.locations.LocationsScreen
import com.example.skycheck.presentation.screen.onboarding.OnboardingScreen
import com.example.skycheck.presentation.theme.SkyCheckTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SkyCheckTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = UiRoutes.Onboarding) {
                    composable<UiRoutes.Onboarding> {
                        OnboardingScreen(navController = navController)
                    }
                    composable<UiRoutes.Locations>(
                        enterTransition = {
                            slideIntoContainer(
                                animationSpec = tween(500, easing = Ease),
                                towards = AnimatedContentTransitionScope.SlideDirection.Left
                            )
                        },
                        exitTransition = {
                            slideOutOfContainer(
                                animationSpec = tween(500, easing = Ease),
                                towards = AnimatedContentTransitionScope.SlideDirection.Right
                            )
                        },
                        popExitTransition = {
                            slideOutOfContainer(
                                animationSpec = tween(500, easing = Ease),
                                towards = AnimatedContentTransitionScope.SlideDirection.Right
                            )
                        }
                    ) {
                        LocationsScreen(navController = navController)
                    }
                    composable<UiRoutes.Forecasts> { entry ->
                        val needReload = entry.toRoute<UiRoutes.Forecasts>().needReload
                        ForecastsScreen(navController = navController, needReload = needReload)
                    }
                }
            }
        }
    }
}