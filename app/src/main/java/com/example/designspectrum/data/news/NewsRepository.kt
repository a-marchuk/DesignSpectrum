package com.example.designspectrum.data.news

import android.util.Log
import com.example.designspectrum.data.api.newsApi.ApiResponse
import com.example.designspectrum.data.api.newsApi.NewsApiService
import com.example.designspectrum.utils.Constants
import retrofit2.Response
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val mapper: NewsMapper,
    private val newsApiService: NewsApiService
) {

    suspend fun getListNews(): List<News> {
        return try {
            when (val apiResponse: ApiResponse<NewsResponse> = getNewsFromApi("ukraine")) {
                is ApiResponse.Success -> {
                    val newsDtoList = apiResponse.data.articles.map { it.toNewsDto() }
                    Log.e("API Response", "Response success")
                    return newsDtoList.take(5).map { newItem -> mapper.map(newItem) }
                }
                is ApiResponse.Error -> {
                    handleApiError(apiResponse.errorMessage)
                    emptyList()
                }
            }
        } catch (e: Exception) {
            handleExceptionError(e)
            emptyList()
        }
    }

    private fun handleApiError(errorData: String) {
        if (errorData == "apiKeyMissing") {
            Log.e("API Error", "API Key is missing")
        } else {
            Log.e("API Error", "Unknown error: $errorData")
        }
    }

    private fun handleExceptionError(throwable: Throwable) {
        Log.e("getListNews", "Error: ${throwable.message}", throwable)
    }

    private suspend fun getNewsFromApi(query: String): ApiResponse<NewsResponse> {
        return runCatching {
            val response: Response<NewsResponse> = newsApiService.getNews(query, Constants.API_KEY)
            if (response.isSuccessful) {
                ApiResponse.Success(response.body() ?: NewsResponse("", 0, emptyList()))
            } else {
                ApiResponse.Error("API request failed: ${response.code()}")
            }
        }.getOrElse {
            ApiResponse.Error(it.localizedMessage ?: "Unknown error")
        }
    }
}






