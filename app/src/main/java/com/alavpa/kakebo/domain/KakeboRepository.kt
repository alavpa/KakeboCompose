package com.alavpa.kakebo.domain

import com.alavpa.kakebo.domain.models.Line
import kotlinx.coroutines.flow.Flow

interface KakeboRepository {
    suspend fun insertNewLine(line: Line)
    fun getAllLines(): Flow<List<Line>>
    fun getOutcomeLines(): Flow<List<Line>>
    fun getIncomeLines(): Flow<List<Line>>
}