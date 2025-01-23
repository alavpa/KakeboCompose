package com.alavpa.kakebo.domain

import com.alavpa.kakebo.domain.models.Line
import kotlinx.coroutines.flow.Flow

interface KakeboRepository {
    fun insertNewLine(line: Line): Flow<Result<Unit>>

    fun removeLine(id: Long): Flow<Result<Unit>>

    fun getAllLines(): Flow<Result<List<Line>>>

    fun setSavings(savings: Int): Flow<Result<Unit>>

    fun getSavings(): Flow<Result<Int>>
}
