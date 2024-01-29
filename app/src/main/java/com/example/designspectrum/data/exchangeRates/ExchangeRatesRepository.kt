package com.example.designspectrum.data.exchangeRates

import android.util.Log
import com.example.designspectrum.data.api.currencyApi.CurrencyApiResponse
import com.example.designspectrum.data.api.currencyApi.ExchangeRateApiService
import retrofit2.Response
import javax.inject.Inject

class ExchangeRatesRepository @Inject constructor(
    private val exchangeRatesMapper: ExchangeRatesMapper,
    private val exchangeRateApiService: ExchangeRateApiService
) {

    suspend fun getExchangeRates(): ExchangeRates {
        return try {
            when (val currencyApiResponse: CurrencyApiResponse<CurrencyResponse> = getCurrencyRateFromApi()) {
                is CurrencyApiResponse.Success -> {
                    val selectedConversionRates = currencyApiResponse.data.selectedConversionRates
                    run {
                        Log.e("Exchange Response", "Response success")
                        exchangeRatesMapper.map(selectedConversionRates.toCurrencyDto())
                    }
                }
                is CurrencyApiResponse.Error -> {
                    Log.e("Exchange Response", "Response error: ${currencyApiResponse.errorMessage}")
                    ExchangeRates(0.0, 0.0, 0.0)
                }
            }
        } catch (e: Exception) {
            Log.e("Exchange Response Exception", "Exception", e)
            ExchangeRates(0.0, 0.0, 0.0)
        }
    }

    private suspend fun getCurrencyRateFromApi(): CurrencyApiResponse<CurrencyResponse> {
        return runCatching {
            val response: Response<CurrencyResponse> = exchangeRateApiService.getExchangeRate()
            if (response.isSuccessful) {
                CurrencyApiResponse.Success(response.body() ?: getDefaultCurrencyResponse())
            } else {
                CurrencyApiResponse.Error("API request failed: ${response.code()}")
            }
        }.getOrElse {
            CurrencyApiResponse.Error(it.localizedMessage ?: "Unknown error")
        }
    }

    private fun getDefaultCurrencyResponse(): CurrencyResponse {
        return CurrencyResponse(
            base_code = "",
            selectedConversionRates  = SelectedConversionRates(),
            documentation = "",
            result = "",
            terms_of_use = "",
            time_last_update_unix = 0,
            time_last_update_utc = "",
            time_next_update_unix = 0,
            time_next_update_utc = ""
        )
    }
}
