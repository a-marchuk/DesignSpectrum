package com.example.designspectrum.data.api.currencyApi

import com.example.designspectrum.data.currency.CurrencyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApiService {
    @GET("latest")
    suspend fun getExchangeRate(
        @Query("") currency: String,
    ): Response<CurrencyResponse>
}