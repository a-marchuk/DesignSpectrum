package com.example.designspectrum.data.api.currencyApi

import com.example.designspectrum.data.exchangeRates.CurrencyResponse
import retrofit2.Response
import retrofit2.http.GET

interface ExchangeRateApiService {
    @GET("latest/UAH")
    suspend fun getExchangeRate(): Response<CurrencyResponse>
}
