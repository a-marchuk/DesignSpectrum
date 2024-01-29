package com.example.designspectrum.data.exchangeRates

import javax.inject.Inject

class ExchangeRatesMapper @Inject constructor(){
    fun map(currencyDto: CurrencyDto): ExchangeRates {
        return ExchangeRates(currencyDto.usd, currencyDto.eur, currencyDto.gbp)
    }
}