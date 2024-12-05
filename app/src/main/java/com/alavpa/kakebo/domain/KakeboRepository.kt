package com.alavpa.kakebo.domain

import com.alavpa.kakebo.domain.models.Line

interface KakeboRepository {
    suspend fun insertNewLine(line: Line)
    suspend fun getAllLines(): List<Line>
}