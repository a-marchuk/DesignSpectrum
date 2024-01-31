package com.example.designspectrum.data.exchangeRates

import android.util.Log
import com.example.designspectrum.data.api.currencyApi.CurrencyApiResponse
import com.example.designspectrum.data.api.currencyApi.ExchangeRateApiService
import kotlinx.coroutines.flow.first
import retrofit2.Response
import javax.inject.Inject

class ExchangeRatesRepository @Inject constructor(
    private val exchangeRatesMapper: ExchangeRatesMapper,
    private val exchangeRateApiService: ExchangeRateApiService,
    private val exchangeRatesDataStoreManager: ExchangeRatesDataStoreManager
) {

    suspend fun getExchangeRates(): ExchangeRates {
        val storedRates = exchangeRatesDataStoreManager.exchangeRatesFlow.first()

        return if (storedRates.areAllZero()) {
            fetchAndStoreExchangeRates()
        } else {
            storedRates
        }
    }

    private suspend fun fetchAndStoreExchangeRates(): ExchangeRates {
        return try {
            when (val currencyApiResponse: CurrencyApiResponse<CurrencyResponse> = getCurrencyRateFromApi()) {
                is CurrencyApiResponse.Success -> {
                    val selectedConversionRates = currencyApiResponse.data.selectedConversionRates
                    val exchangeRates = exchangeRatesMapper.map(selectedConversionRates.toCurrencyDto())

                    exchangeRatesDataStoreManager.saveExchangeRates(exchangeRates)

                    exchangeRates
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
