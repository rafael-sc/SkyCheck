package com.example.skycheck.di

import com.example.skycheck.presentation.screen.current_location.CurrentLocationViewModel
import com.example.skycheck.presentation.screen.locations.LocationsViewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { CurrentLocationViewModel() }
    single { LocationsViewModel() }
}