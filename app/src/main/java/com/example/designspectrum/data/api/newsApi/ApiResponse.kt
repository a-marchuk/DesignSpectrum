package com.example.designspectrum.data.api.newsApi

sealed class ApiResponse<out T> {
    data class Success<T>(val data: T) : ApiResponse<T>()
    data class Error<T>(val errorMessage: String, val errorData: T? = null) : ApiResponse<T>()
}
