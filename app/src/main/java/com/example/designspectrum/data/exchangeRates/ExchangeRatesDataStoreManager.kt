package com.example.designspectrum.data.exchangeRates

import android.content.Context
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.exchangeRatesDataStore by preferencesDataStore("exchange_rates")

class ExchangeRatesDataStoreManager(context: Context) {

    private val dataStore = context.exchangeRatesDataStore

    val exchangeRatesFlow: Flow<ExchangeRates> = dataStore.data.map { preferences ->
        ExchangeRates(
            (preferences[EUR_KEY] ?: 0.0),
            (preferences[USD_KEY] ?: 0.0),
            (preferences[GBP_KEY] ?: 0.0)
        )
    }

    suspend fun saveExchangeRates(rates: ExchangeRates) {
        dataStore.edit { preferences ->
            preferences[EUR_KEY] = rates.eur as Double
            preferences[USD_KEY] = rates.usd as Double
            preferences[GBP_KEY] = rates.gbp as Double
        }
    }

    companion object {
        private val EUR_KEY = doublePreferencesKey("eur")
        private val USD_KEY = doublePreferencesKey("usd")
        private val GBP_KEY = doublePreferencesKey("gbp")
    }
}
