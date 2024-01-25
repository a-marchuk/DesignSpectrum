package com.example.designspectrum.data.news

import com.example.designspectrum.data.api.newsApi.ApiResponse
import com.example.designspectrum.data.api.newsApi.NewsApiService
import com.example.designspectrum.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val mapper: NewsMapper,
    private val newsApiService: NewsApiService
    ){
    suspend fun getListNews(): List<News> = withContext(Dispatchers.IO) {
        try {
            when (val apiResponse: ApiResponse<NewsResponse> = getNewsFromApi("furniture")) {
                is ApiResponse.Success -> {
                    val newsDtoList = apiResponse.data.articles.map { it.toNewsDto() }
                    return@withContext newsDtoList.map { newItem -> mapper.map(newItem) }
                }
                is ApiResponse.Error -> {
                    return@withContext emptyList()
                }
            }
        } catch (e: Exception) {
            return@withContext emptyList()
        }
    }

    private fun getNewsFromApi(query: String): ApiResponse<NewsResponse> {
        return try {
            val response: Response<NewsResponse> =
                newsApiService.getNews(query, Constants.API_KEY)
            if (response.isSuccessful) {
                ApiResponse.Success(response.body() ?: NewsResponse("", 0, emptyList()))
            } else {
                ApiResponse.Error("API request failed")
            }
        } catch (e: Exception) {
            ApiResponse.Error(e.localizedMessage ?: "Unknown error")
        }
    }

}





