package com.alavpa.kakebo.data.db.di

import com.alavpa.kakebo.data.db.DbDatasource
import com.alavpa.kakebo.data.db.DbDatasourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DbDatasourceModule {
    @Binds
    abstract fun provideDbDataSource(dbDatasourceImpl: DbDatasourceImpl): DbDatasource
}
