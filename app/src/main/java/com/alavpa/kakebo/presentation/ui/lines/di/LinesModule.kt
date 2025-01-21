package com.alavpa.kakebo.presentation.ui.lines.di

import com.alavpa.kakebo.presentation.ui.lines.LinesState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object LinesModule {
    @Provides
    fun providesInitialLinesSate(): LinesState = LinesState.INITIAL
}
