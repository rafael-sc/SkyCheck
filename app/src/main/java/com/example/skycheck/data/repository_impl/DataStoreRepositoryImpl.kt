package com.example.skycheck.data.repository_impl

import com.example.skycheck.data.manager.DataStoreManager
import com.example.skycheck.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow

class DataStoreRepositoryImpl(
    private val dataStoreManager: DataStoreManager
) : DataStoreRepository {
    override suspend fun setOnboardingDone() {
        dataStoreManager.setOnboardingDone()
    }

    override suspend fun getOnboardingDone(): Flow<Boolean?> {
        return dataStoreManager.getOnboardingDone()
    }
}