package com.example.designspectrum.data.news

import javax.inject.Inject

class NewsMapper @Inject constructor(){
    fun map(newsDto: NewsDto): News {
        return News(newsDto.imageURL, newsDto.title, newsDto.newsURL)
    }
}