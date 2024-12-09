package com.alavpa.kakebo.data.db

import com.alavpa.kakebo.data.mappers.LineDataMapper
import com.alavpa.kakebo.domain.KakeboRepository
import com.alavpa.kakebo.domain.models.Line
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class KakeboDataRepository @Inject constructor(
    private val dbDatasource: DbDatasource,
    private val lineDataMapper: LineDataMapper
) : KakeboRepository {
    override suspend fun insertNewLine(line: Line) {
        return dbDatasource.insert(lineDataMapper.from(line))
    }

    override fun getAllLines(): Flow<List<Line>> {
        return dbDatasource.getAll().map { linesData ->
            linesData.map { lineDataMapper.to(it) }
        }
    }
}