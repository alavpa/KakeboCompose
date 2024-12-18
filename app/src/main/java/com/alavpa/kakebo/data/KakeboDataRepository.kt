package com.alavpa.kakebo.data

import com.alavpa.kakebo.data.db.DbDatasource
import com.alavpa.kakebo.data.mappers.LineDataMapper
import com.alavpa.kakebo.data.preferences.PreferencesDatasource
import com.alavpa.kakebo.domain.KakeboRepository
import com.alavpa.kakebo.domain.models.Line
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class KakeboDataRepository @Inject constructor(
    private val dbDatasource: DbDatasource,
    private val lineDataMapper: LineDataMapper,
    private val preferencesDatasource: PreferencesDatasource
) : KakeboRepository {
    override suspend fun insertNewLine(line: Line) {
        val lineData = lineDataMapper.from(line)
        dbDatasource.insert(lineData)
    }

    override fun getAllLines(): Flow<List<Line>> {
        return dbDatasource.getAll().map { linesData ->
            linesData.map { lineDataMapper.to(it) }
        }
    }

    override suspend fun setSavings(savings: Long) {
        preferencesDatasource.setSavings(savings)
    }

    override fun getSavings(): Flow<Long> = preferencesDatasource.getSavings()
}
