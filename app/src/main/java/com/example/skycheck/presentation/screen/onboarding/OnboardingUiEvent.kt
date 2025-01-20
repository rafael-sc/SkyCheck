package com.example.skycheck.presentation.screen.onboarding

import android.content.Context

sealed class OnboardingUiEvent {
    data class OnSetFusedLocationProviderClient(val context: Context) : OnboardingUiEvent()
}