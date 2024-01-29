package com.example.designspectrum.di

import com.example.designspectrum.data.api.newsApi.NewsApiService
import com.example.designspectrum.utils.Constants.Companion.NEWS_API_BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NewsApiModule {

    @Provides
    @Singleton
    @Named("NewsApiRetrofit")
    fun provideNewsApiRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(NEWS_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideNewsApiService(@Named("NewsApiRetrofit") retrofit: Retrofit): NewsApiService =
        retrofit.create(NewsApiService::class.java)
}
