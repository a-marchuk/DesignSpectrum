package com.example.designspectrum.data.api.newsApi

sealed class NewsApiResponse<out T> {
    data class Success<T>(val data: T) : NewsApiResponse<T>()
    data class Error<T>(val errorMessage: String, val errorData: T? = null) : NewsApiResponse<T>()
}
