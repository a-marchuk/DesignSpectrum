package com.example.designspectrum.data.currency

import com.example.designspectrum.data.api.currencyApi.CurrencyApiResponse
import com.example.designspectrum.data.api.currencyApi.CurrencyApiService
import retrofit2.Response
import javax.inject.Inject

class CurrencyRepository @Inject constructor(
    private val currencyMapper: CurrencyMapper,
    private val currencyApiService: CurrencyApiService
) {

    suspend fun getExchangeRate(): Currency {
        return try {
            when (val currencyApiResponse: CurrencyApiResponse<CurrencyResponse> = getCurrencyRateFromApi("UAH")) {
                is CurrencyApiResponse.Success -> {
                    val currencyResponse = currencyApiResponse.data
                    return currencyMapper.map(currencyResponse.selectedConversionRates.toCurrencyDto())
                }
                is CurrencyApiResponse.Error -> {
                    Currency(0.0,0.0,0.0)
                }
            }
        } catch (e: Exception) {
            Currency(0.0,0.0,0.0)
        }
    }


    private suspend fun getCurrencyRateFromApi(query: String): CurrencyApiResponse<CurrencyResponse> {
        return try {
            val response: Response<CurrencyResponse> = currencyApiService.getExchangeRate(query)

            if (response.isSuccessful) {
                val currencyResponse = response.body()
                CurrencyApiResponse.Success(currencyResponse ?: getDefaultCurrencyResponse())
            } else {
                CurrencyApiResponse.Error("API request failed: ${response.code()}")
            }
        } catch (e: Exception) {
            CurrencyApiResponse.Error(e.localizedMessage ?: "Unknown error")
        }
    }

    private fun getDefaultCurrencyResponse(): CurrencyResponse {
        return CurrencyResponse(
            base_code = "",
            selectedConversionRates = SelectedConversionRates(0.0, 0.0, 0.0),
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
