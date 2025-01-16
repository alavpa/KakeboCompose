package com.alavpa.kakebo.data.db

import com.alavpa.kakebo.data.db.dao.LineDao
import com.alavpa.kakebo.data.db.entities.LineData
import com.alavpa.kakebo.utils.CalendarUtils
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

interface DbDatasource {

    suspend fun insert(lineData: LineData)

    suspend fun removeLine(id: Long)

    fun getAll(): Flow<List<LineData>>
}

class DbDatasourceImpl @Inject constructor(
    private val calendarUtils: CalendarUtils,
    private val lineDao: LineDao
) : DbDatasource {

    override suspend fun insert(lineData: LineData) = lineDao.insert(lineData)
    override suspend fun removeLine(id: Long) = lineDao.remove(id)


    override fun getAll(): Flow<List<LineData>> = lineDao.getAllFromCurrentMonthYear(
        calendarUtils.getCurrentMonth(),
        calendarUtils.getCurrentYear()
    )
}
