package com.alavpa.kakebo.data.db.di

import android.content.Context
import androidx.room.Room
import com.alavpa.kakebo.data.db.KakeboDatabase
import com.alavpa.kakebo.data.db.dao.LineDao
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [DbModule::class])
class DbModuleTest {
    @Provides
    @Singleton
    fun provideDb(
        @ApplicationContext context: Context
    ): KakeboDatabase {
        return Room.inMemoryDatabaseBuilder(context, KakeboDatabase::class.java).build()
    }

    @Provides
    fun provideDao(db: KakeboDatabase): LineDao = db.lineDao()
}
