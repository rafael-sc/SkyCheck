package com.example.skycheck

import android.app.Application
import com.example.skycheck.di.databaseModule
import com.example.skycheck.di.locationModule
import com.example.skycheck.di.networkModule
import com.example.skycheck.di.repositoryModule
import com.example.skycheck.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@Application)
            modules(
                networkModule,
                repositoryModule,
                viewModelModule,
                databaseModule,
                locationModule
            )
        }
    }
}