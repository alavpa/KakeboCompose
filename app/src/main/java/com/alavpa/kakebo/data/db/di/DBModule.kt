package com.alavpa.kakebo.data.db.di

import android.content.Context
import androidx.room.Room
import com.alavpa.kakebo.data.db.KakeboDatabase
import com.alavpa.kakebo.data.db.dao.LineDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDb(@ApplicationContext context: Context): KakeboDatabase =
        Room.databaseBuilder(context, KakeboDatabase::class.java, "kakebo.db").build()

    @Provides
    fun provideDao(db: KakeboDatabase): LineDao = db.lineDao()
}