package com.example.skycheck.data.manager

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.skycheck.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = Constants.USER_PREFERENCES)

class DataStoreManager(private val context: Context) {

    private val ONBOARDING_DONE = booleanPreferencesKey(name = Constants.ONBOARDING_DONE)

    suspend fun setOnboardingDone() {
        context.dataStore.edit { preferences ->
            preferences[ONBOARDING_DONE] = true
        }
    }

    fun getOnboardingDone(): Flow<Boolean?> = context.dataStore.data.map { preferences ->
        preferences[ONBOARDING_DONE]
    }
}
