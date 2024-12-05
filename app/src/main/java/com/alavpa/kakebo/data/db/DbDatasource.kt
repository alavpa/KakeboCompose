package com.alavpa.kakebo.data.db

import com.alavpa.kakebo.data.db.dao.LineDao
import com.alavpa.kakebo.data.db.entities.LineData
import com.alavpa.kakebo.data.model.TypeData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface DbDatasource {
    suspend fun insert(lineData: LineData)
    fun getAll(): Flow<List<LineData>>
    fun getByType(type: TypeData): Flow<List<LineData>>
}

class DbDatasourceImpl @Inject constructor(val lineDao: LineDao) : DbDatasource {
    override suspend fun insert(lineData: LineData) = lineDao.insert(lineData)
    override fun getAll(): Flow<List<LineData>> = lineDao.getAll()
    override fun getByType(type: TypeData): Flow<List<LineData>> = lineDao.getByType(type)
}