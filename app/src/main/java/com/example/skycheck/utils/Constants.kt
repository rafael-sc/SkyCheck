package com.example.skycheck.utils

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.skycheck.R
import java.time.LocalDate

object Constants {
    const val OPEN_WEATHER_BASE_URL = "https://api.openweathermap.org/"

    const val USER_PREFERENCES = "userPreferences"
    const val ONBOARDING_DONE = "onboardingDone"

    @RequiresApi(Build.VERSION_CODES.O)
    val TODAY: LocalDate = LocalDate.now()
}