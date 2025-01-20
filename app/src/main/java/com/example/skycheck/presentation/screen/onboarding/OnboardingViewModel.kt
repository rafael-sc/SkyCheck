package com.example.skycheck.presentation.screen.onboarding

import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class OnboardingViewModel() : ViewModel() {
    private var _fusedLocationClient: FusedLocationProviderClient? = null

    fun onEvent(event: OnboardingUiEvent) {
        when (event) {
            is OnboardingUiEvent.OnSetFusedLocationProviderClient -> setFusedLocationProviderClient(
                context = event.context
            )
        }
    }

    private fun setFusedLocationProviderClient(context: Context) {
        _fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    }
}