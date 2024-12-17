package com.alavpa.kakebo.presentation.ui.lines.di

import com.alavpa.kakebo.presentation.ui.lines.AddLinesState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object AddLinesModule {
    @Provides
    fun providesInitialViewModel() = AddLinesState.INITIAL
}
