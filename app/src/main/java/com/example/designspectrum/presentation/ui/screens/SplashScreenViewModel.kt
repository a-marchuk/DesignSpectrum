package com.example.designspectrum.presentation.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.designspectrum.data.exchangeRates.ExchangeRatesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val exchangeRatesRepository: ExchangeRatesRepository
): ViewModel() {
    private val _isReady = MutableStateFlow(false)
    val isReady = _isReady.asStateFlow()

    init {
        viewModelScope.launch {
            delay(3000L)
            _isReady.value=true
        }
    }

    fun initExchangeRates() {
        viewModelScope.launch {
            exchangeRatesRepository.fetchAndStoreExchangeRates()
        }
    }
}