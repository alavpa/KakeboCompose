package com.alavpa.kakebo.utils

import android.icu.text.DateFormat
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.Calendar

@Module
@InstallIn(SingletonComponent::class)
object CalendarModule {

    @Provides
    fun provideCalendar(): Calendar = Calendar.getInstance()

    @Provides
    fun provideDateFormat(): DateFormat = DateFormat.getPatternInstance(DateFormat.YEAR_ABBR_MONTH)
}
