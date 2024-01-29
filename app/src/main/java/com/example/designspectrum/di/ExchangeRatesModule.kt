package com.example.designspectrum.di

import com.example.designspectrum.data.api.currencyApi.ExchangeRateApiService
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
object ExchangeRateModule {

    @Provides
    @Singleton
    @Named("ExchangeRateApiRetrofit")
    fun provideExchangeRateApiRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://v6.exchangerate-api.com/v6/5601f08b70b728a537736ac8/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    fun provideExchangeRateApiService(@Named("ExchangeRateApiRetrofit") retrofit: Retrofit): ExchangeRateApiService =
        retrofit.create(ExchangeRateApiService::class.java)
}
