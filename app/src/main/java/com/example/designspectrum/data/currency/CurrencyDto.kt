package com.example.designspectrum.data.currency

data class CurrencyDto(
    val usd : Double,
    val eur : Double,
    val gbp: Double
)
data class CurrencyResponse(
    val base_code: String,
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
    val USD: Double,
    val EUR: Double,
    val GBP: Double
) {
    fun toCurrencyDto(): CurrencyDto{
        return CurrencyDto(
            usd = this.USD,
            eur = this.EUR,
            gbp = this.GBP
        )
    }
}
