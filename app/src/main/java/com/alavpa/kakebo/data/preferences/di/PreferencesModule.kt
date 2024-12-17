package com.alavpa.kakebo.data.preferences.di

import com.alavpa.kakebo.data.preferences.KakeboDataStore
import com.alavpa.kakebo.data.preferences.PreferencesDatasource
import com.alavpa.kakebo.data.preferences.PreferencesDatasourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {
    @Provides
    fun providePreferencesDatasource(dataStore: KakeboDataStore): PreferencesDatasource = PreferencesDatasourceImpl(dataStore)
}
