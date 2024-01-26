package com.example.designspectrum.data.api.currencyApi

sealed class CurrencyApiResponse<out T> {
    data class Success<T>(val data: T) : CurrencyApiResponse<T>()
    data class Error<T>(val errorMessage: String, val errorData: T? = null) : CurrencyApiResponse<T>()
}
