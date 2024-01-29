package com.example.designspectrum.data.exchangeRates

import com.google.gson.annotations.SerializedName

data class CurrencyDto(
    val usd: Double?,
    val eur: Double?,
    val gbp: Double?
)
data class CurrencyResponse(
    val base_code: String,
    @SerializedName("conversion_rates")
    val selectedConversionRates: SelectedConversionRates,
    val documentation: String,
    val result: String,
    val terms_of_use: String,
    val time_last_update_unix: Int,
    val time_last_update_utc: String,
    val time_next_update_unix: Int,
    val time_next_update_utc: String
)

data class SelectedConversionRates(
    val USD: Double? = null,
    val EUR: Double? = null,
    val GBP: Double? = null
) {
    fun toCurrencyDto(): CurrencyDto{
        return CurrencyDto(
            usd = this.USD,
            eur = this.EUR,
            gbp = this.GBP
        )
    }
}
