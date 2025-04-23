package com.example.skycheck.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    suspend fun setOnboardingDone()

    suspend fun getOnboardingDone(): Flow<Boolean?>
}