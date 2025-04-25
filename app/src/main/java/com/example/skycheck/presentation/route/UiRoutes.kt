package com.example.skycheck.presentation.route

import kotlinx.serialization.Serializable

@Serializable
object UiRoutes {
    @Serializable
    data object Onboarding

    @Serializable
    data class Forecasts(val needReload: Boolean)

    @Serializable
    data object Locations
}