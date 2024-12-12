package com.example.skycheck.di

import com.example.skycheck.data.repository_impl.OpenWeatherRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single { OpenWeatherRepositoryImpl(get()) }
}