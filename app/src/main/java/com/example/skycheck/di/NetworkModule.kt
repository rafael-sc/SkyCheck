package com.example.skycheck.di

import com.example.skycheck.data.api.OpenWeatherApi
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val OPEN_WEATHER_BASE_URL = "https://api.openweathermap.org/data/2.5/"

val networkModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(OPEN_WEATHER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get<OkHttpClient>())
            .build()
            .create(OpenWeatherApi::class.java)
    }
}