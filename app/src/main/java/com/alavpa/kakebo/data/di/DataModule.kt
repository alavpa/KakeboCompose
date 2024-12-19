package com.alavpa.kakebo.data.di

import com.alavpa.kakebo.data.KakeboDataRepository
import com.alavpa.kakebo.data.db.DbDatasource
import com.alavpa.kakebo.data.mappers.LineDataMapper
import com.alavpa.kakebo.data.preferences.PreferencesDatasource
import com.alavpa.kakebo.domain.KakeboRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    fun provideKakeboRepository(
        dbDatasource: DbDatasource,
        linearDataMapper: LineDataMapper,
        preferencesDatasource: PreferencesDatasource
    ): KakeboRepository =
        KakeboDataRepository(dbDatasource, linearDataMapper, preferencesDatasource)
}
