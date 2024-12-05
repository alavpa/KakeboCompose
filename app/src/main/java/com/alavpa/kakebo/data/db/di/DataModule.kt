package com.alavpa.kakebo.data.db.di

import com.alavpa.kakebo.data.db.KakeboDataRepository
import com.alavpa.kakebo.domain.KakeboRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun provideKakeboRepository(
        kakeboDataRepository: KakeboDataRepository
    ): KakeboRepository
}