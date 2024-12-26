package com.alavpa.kakebo.domain

import com.alavpa.kakebo.domain.models.Line
import kotlinx.coroutines.flow.Flow

interface KakeboRepository {
    suspend fun insertNewLine(line: Line)

    fun getAllLines(): Flow<List<Line>>

    suspend fun setSavings(savings: String)

    fun getSavings(): Flow<String>
}
