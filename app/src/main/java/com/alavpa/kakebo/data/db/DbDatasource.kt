package com.alavpa.kakebo.data.db

import com.alavpa.kakebo.data.db.dao.LineDao
import com.alavpa.kakebo.data.db.entities.LineData
import javax.inject.Inject

interface DbDatasource {
    suspend fun insert(lineData: LineData)
    suspend fun getAll(): List<LineData>
}

class DbDatasourceImpl @Inject constructor(val lineDao: LineDao) : DbDatasource {
    override suspend fun insert(lineData: LineData) = lineDao.insert(lineData)
    override suspend fun getAll(): List<LineData> = lineDao.getAll()
}