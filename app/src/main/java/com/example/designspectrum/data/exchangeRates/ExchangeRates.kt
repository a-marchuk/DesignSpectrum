package com.example.designspectrum.data.exchangeRates

data class ExchangeRates(
    val eur: Double?,
    val usd: Double?,
    val gbp: Double?
)

fun ExchangeRates.areAllZero(): Boolean {
    return eur == 0.0 && usd == 0.0 && gbp == 0.0
}
