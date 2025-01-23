package com.alavpa.kakebo.data

import com.alavpa.kakebo.data.db.DbDatasource
import com.alavpa.kakebo.data.mappers.LineDataMapper
import com.alavpa.kakebo.data.preferences.PreferencesDatasource
import com.alavpa.kakebo.domain.KakeboRepository
import com.alavpa.kakebo.domain.models.Line
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class KakeboDataRepository @Inject constructor(
    private val dbDatasource: DbDatasource,
    private val lineDataMapper: LineDataMapper,
    private val preferencesDatasource: PreferencesDatasource
) : KakeboRepository {
    override fun insertNewLine(line: Line) = flow {
        dbDatasource.insert(lineDataMapper.from(line))
        emit(Result.success(Unit))
    }.catch {
        emit(Result.failure(it))
    }

    override fun removeLine(id: Long): Flow<Result<Unit>> = flow {
        dbDatasource.removeLine(id)
        emit(Result.success(Unit))
    }.catch {
        emit(Result.failure(it))
    }

    override fun getAllLines(): Flow<Result<List<Line>>> {
        return dbDatasource.getAll().map { linesData ->
            Result.success(linesData.map { lineDataMapper.to(it) })
        }.catch { emit(Result.failure(it)) }
    }

    override fun setSavings(savings: Int): Flow<Result<Unit>> = flow {
        preferencesDatasource.setSavings(savings)
        emit(Result.success(Unit))
    }.catch {
        emit(Result.failure(it))
    }

    override fun getSavings(): Flow<Result<Int>> = preferencesDatasource.getSavings()
        .map { Result.success(it) }
        .catch { emit(Result.failure(it)) }
}
