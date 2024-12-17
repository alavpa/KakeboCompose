package com.alavpa.kakebo.presentation.ui.statistics.di

import com.alavpa.kakebo.presentation.ui.statistics.StatisticsState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object StatisticsModule {
    @Provides
    fun providesInitialState(): StatisticsState = StatisticsState.INITIAL
}
