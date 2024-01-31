package com.example.designspectrum.data.news

import android.util.Log
import com.example.designspectrum.data.api.newsApi.NewsApiResponse
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
            when (val newsApiResponse: NewsApiResponse<NewsResponse> = getNewsFromApi("Winter")) {
                is NewsApiResponse.Success -> {
                    val newsDtoList = newsApiResponse.data.articles.map { it.toNewsDto() }
                    Log.e("API Response", "Response success")
                    return newsDtoList.take(5).map { newItem -> mapper.map(newItem) }
                }
                is NewsApiResponse.Error -> {
                    handleApiError(newsApiResponse.errorMessage)
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

    private suspend fun getNewsFromApi(query: String): NewsApiResponse<NewsResponse> {
        return runCatching {
            val response: Response<NewsResponse> = newsApiService.getNews(query, Constants.NEWS_API_KEY)
            if (response.isSuccessful) {
                NewsApiResponse.Success(response.body() ?: NewsResponse("", 0, emptyList()))
            } else {
                NewsApiResponse.Error("API request failed: ${response.code()}")
            }
        }.getOrElse {
            NewsApiResponse.Error(it.localizedMessage ?: "Unknown error")
        }
    }
}






