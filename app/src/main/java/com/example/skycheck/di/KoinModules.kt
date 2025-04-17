package com.example.skycheck.di

import androidx.room.Room
import com.example.skycheck.data.api.OpenWeatherApi
import com.example.skycheck.data.model.database.SkyCheckDatabase
import com.example.skycheck.data.repository_impl.LocationRepositoryImpl
import com.example.skycheck.data.repository_impl.OpenWeatherRepositoryImpl
import com.example.skycheck.presentation.screen.forecasts.ForecastsViewModel
import com.example.skycheck.presentation.screen.locations.LocationsViewModel
import com.example.skycheck.presentation.screen.onboarding.OnboardingViewModel
import com.example.skycheck.utils.Constants.OPEN_WEATHER_BASE_URL
import com.google.android.gms.location.LocationServices
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val databaseModule = module {
    single {
        Room.databaseBuilder(
            context = androidContext(),
            klass = SkyCheckDatabase::class.java,
            name = "skycheck"
        ).build()
    }

    single { get<SkyCheckDatabase>().locationDao }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .cache(null)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
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

val repositoryModule = module {
    single { OpenWeatherRepositoryImpl(get()) }
    single { LocationRepositoryImpl(get(), get()) }
}

val viewModelModule = module {
    single { ForecastsViewModel(get(), get()) }
    single { LocationsViewModel(get(), get()) }
    single { OnboardingViewModel() }
}

val locationModule = module {
    single { LocationServices.getFusedLocationProviderClient(androidContext().applicationContext) }
}