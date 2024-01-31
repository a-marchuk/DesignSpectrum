package com.example.designspectrum.di

import android.content.Context
import com.example.designspectrum.data.selectedCurrency.SelectedRadioButtonDataStoreManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStorePreferencesModule {

    @Provides
    @Singleton
    fun provideSelectedRadioButtonDataStoreManager(@ApplicationContext appContext: Context):
            SelectedRadioButtonDataStoreManager =
        SelectedRadioButtonDataStoreManager(appContext)
}
