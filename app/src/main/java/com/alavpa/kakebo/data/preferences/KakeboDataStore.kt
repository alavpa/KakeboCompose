package com.alavpa.kakebo.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KakeboDataStore @Inject constructor(@ApplicationContext val context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    private val savingsKey = longPreferencesKey("savings")
    val savingsFlow: Flow<Long> = context.dataStore.data
        .map { preferences -> preferences[savingsKey] ?: 0L }

    suspend fun save(savings: Long) {
        context.dataStore.edit { settings ->
            settings[savingsKey] = savings
        }
    }
}