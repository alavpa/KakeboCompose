package com.alavpa.kakebo.data.preferences

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface PreferencesDatasource {

    suspend fun setSavings(savings: Long)

    fun getSavings(): Flow<Long>
}

class PreferencesDatasourceImpl @Inject constructor(
    private val dataStore: KakeboDataStore
) : PreferencesDatasource {
    override suspend fun setSavings(savings: Long) {
        dataStore.save(savings)
    }

    override fun getSavings(): Flow<Long> = dataStore.savingsFlow
}
