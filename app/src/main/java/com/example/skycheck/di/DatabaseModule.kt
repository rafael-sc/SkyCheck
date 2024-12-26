package com.example.skycheck.di

import androidx.room.Room
import com.example.skycheck.data.model.database.SkyCheckDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

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