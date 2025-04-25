package com.example.skycheck.presentation.screen.onboarding

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skycheck.data.repository_impl.DataStoreRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OnboardingViewModel(
    private val dataStoreRepository: DataStoreRepositoryImpl
) : ViewModel() {

    val isOnboardingDone = MutableStateFlow<Boolean?>(null)

    init {
        viewModelScope.launch {
            isOnboardingDone.update { dataStoreRepository.getOnboardingDone().first() ?: false }
        }
    }

    fun onButtonClick() {
        viewModelScope.launch {
            try {
                dataStoreRepository.setOnboardingDone()
            } catch (e: Exception) {
                Log.e("DataStore", "Something went wrong when saving info in DataStore", e)
            }
        }
    }
}