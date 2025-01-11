package com.alavpa.kakebo.presentation.di

import com.alavpa.kakebo.presentation.utils.CoroutineDispatcherProvider
import com.alavpa.kakebo.presentation.utils.DispatcherProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface DispatcherProviderModule {

    @Binds
    fun providerDispatcherProvider(
        coroutineDispatcherProvider: CoroutineDispatcherProvider
    ): DispatcherProvider
}
