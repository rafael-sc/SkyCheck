package com.example.skycheck.di

import android.util.Log
import com.example.skycheck.data.api.OpenWeatherApi
import com.example.skycheck.utils.Constants.OPEN_WEATHER_BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun logginInterceptor(): HttpLoggingInterceptor {
    Log.d("logginInterceptor", "Iniciando logginInterceptor")

    return HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
}

val networkModule = module {
    // OkHttpClient
    single {
        OkHttpClient.Builder()
            .cache(null)
            .addInterceptor(logginInterceptor())
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(OPEN_WEATHER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get<OkHttpClient>())
            .build()
            .create(OpenWeatherApi::class.java)
    }
}