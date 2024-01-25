package com.example.designspectrum.data.news

import com.example.designspectrum.presentation.ui.fragments.shopp.maison.NewsMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

//TODO add API interface
class  NewsRepository @Inject constructor(
    private val mapper: NewsMapper,
    //TODO newsApi
){
    // TODO move to NewsDto
    suspend fun getListNews(): List<News> = withContext(Dispatchers.IO) {
        // val newsDto = newsApi.getNews()
        // val news = newsDto.map{ newItem -> mapper.map(newsItem)}
        // return news


        return@withContext listOf(
            News("", "1", ""),
            News("", "2", ""),
            News("", "3", ""),
        )
    }

}


