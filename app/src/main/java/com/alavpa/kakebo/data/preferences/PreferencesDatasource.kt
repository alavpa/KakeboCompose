package com.alavpa.kakebo.data.preferences

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

interface PreferencesDatasource {
    suspend fun setSavings(savings: Int)

    fun getSavings(): Flow<Int>
}

class PreferencesDatasourceImpl @Inject constructor(
    private val dataStore: KakeboDataStore
) : PreferencesDatasource {
    override suspend fun setSavings(savings: Int) {
        dataStore.save(savings)
    }

    override fun getSavings(): Flow<Int> = dataStore.savingsFlow()
}
