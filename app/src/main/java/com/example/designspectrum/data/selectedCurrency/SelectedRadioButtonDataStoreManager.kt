package com.example.designspectrum.data.selectedCurrency

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("selected_radio_button")

class SelectedRadioButtonDataStoreManager(appContext: Context) {

    private val dataStore = appContext.dataStore

    companion object {
        private val SELECTED_RADIO_BUTTON_KEY = intPreferencesKey("selected_radio_button")
        private const val DEFAULT_SELECTED_RADIO_BUTTON = 2131362241
    }
    suspend fun saveSelectedRadioButton(selectedId: Int) {
        dataStore.edit { preferences ->
            preferences[SELECTED_RADIO_BUTTON_KEY] = selectedId
            Log.d("DataStore", preferences[SELECTED_RADIO_BUTTON_KEY].toString())
        }
    }

    fun getSelectedRadioButton(): Flow<SelectedRadioButton> =
        dataStore.data.map { preferences ->
            Log.d("DataStore", preferences[SELECTED_RADIO_BUTTON_KEY].toString())
            SelectedRadioButton(
                preferences[SELECTED_RADIO_BUTTON_KEY]
                ?: DEFAULT_SELECTED_RADIO_BUTTON
            )
        }
}
