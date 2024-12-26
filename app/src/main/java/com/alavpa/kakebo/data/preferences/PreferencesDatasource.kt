package com.alavpa.kakebo.data.preferences

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

interface PreferencesDatasource {
    suspend fun setSavings(savings: String)

    fun getSavings(): Flow<String>
}

class PreferencesDatasourceImpl @Inject constructor(
    private val dataStore: KakeboDataStore
) : PreferencesDatasource {
    override suspend fun setSavings(savings: String) {
        dataStore.save(savings)
    }

    override fun getSavings(): Flow<String> = dataStore.savingsFlow()
}
