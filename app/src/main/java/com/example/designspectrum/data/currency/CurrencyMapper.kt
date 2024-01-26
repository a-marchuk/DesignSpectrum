package com.example.designspectrum.data.currency

import javax.inject.Inject

class CurrencyMapper @Inject constructor(){
    fun map(currencyDto: CurrencyDto): Currency {
        return Currency(currencyDto.usd, currencyDto.eur, currencyDto.gbp)
    }
}