package com.alavpa.kakebo.data.db

import app.cash.turbine.test
import com.alavpa.kakebo.data.db.dao.LineDao
import com.alavpa.kakebo.data.db.entities.LineData
import com.alavpa.kakebo.utils.CalendarUtils
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class DbDatasourceImplTest {

    private val calendarUtils: CalendarUtils = mockk()
    private val lineDao: LineDao = mockk()

    private val dbDatasource = DbDatasourceImpl(calendarUtils, lineDao)

    @Test
    fun `when insert should verify call to LineDao insert`() = runTest {
        val lineData: LineData = mockk()
        coEvery { lineDao.insert(any()) } just runs

        dbDatasource.insert(lineData)

        coVerify { lineDao.insert(lineData) }
    }

    @Test
    fun `when get lines should verify call to LineDao get lines with proper params`() = runTest {
        val lineData: LineData = mockk()
        coEvery {
            lineDao.getAllFromCurrentMonthYear(any(), any())
        } returns flowOf(listOf(lineData))
        every { calendarUtils.getCurrentMonth() } returns 11
        every { calendarUtils.getCurrentYear() } returns 2023

        dbDatasource.getAll().test {
            assertEquals(listOf(lineData), awaitItem())
            awaitComplete()
            verify { lineDao.getAllFromCurrentMonthYear(11, 2023) }
        }
    }
}