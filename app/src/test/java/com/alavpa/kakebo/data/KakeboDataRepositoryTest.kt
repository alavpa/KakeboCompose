package com.alavpa.kakebo.data

import app.cash.turbine.test
import com.alavpa.kakebo.data.db.DbDatasource
import com.alavpa.kakebo.data.db.entities.LineData
import com.alavpa.kakebo.data.mappers.LineDataMapper
import com.alavpa.kakebo.data.preferences.KakeboDataStore
import com.alavpa.kakebo.domain.models.Line
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

class KakeboDataRepositoryTest {

    private val dbDatasource: DbDatasource = mockk()
    private val lineDataMapper: LineDataMapper = mockk()
    private val kakeboDataStore: KakeboDataStore = mockk()

    private val repository = KakeboDataRepository(dbDatasource, lineDataMapper, kakeboDataStore)

    @Test
    fun `when insert new line should verify call insert properly`() = runTest {
        val line: Line = mockk()
        val lineData: LineData = mockk()
        every { lineDataMapper.from(any()) } returns lineData
        coEvery { dbDatasource.insert(any()) } just runs

        repository.insertNewLine(line)

        verify { lineDataMapper.from(line) }
        coVerify { dbDatasource.insert(lineData) }
    }

    @Test
    fun `when getAllLine should verify call db properly`() = runTest {
        val line: Line = mockk()
        val lineData: LineData = mockk()
        every { lineDataMapper.to(any()) } returns line
        coEvery { dbDatasource.getAll() } returns flowOf(listOf(lineData))

        repository.getAllLines().test {
            assertEquals(listOf(line), awaitItem())
            awaitComplete()
            verify { dbDatasource.getAll() }
            verify { lineDataMapper.to(lineData) }
        }
    }

    @Test
    fun `when get savings should verify call data store properly`() = runTest {
        every { kakeboDataStore.savingsFlow } returns flowOf(1200)

        repository.getSavings().test {
            assertEquals(1200, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `when set savings should verify call data store properly`() = runTest {
        coEvery { kakeboDataStore.save(1200) } just runs

        repository.setSavings(1200)

        coVerify { kakeboDataStore.save(1200) }
    }
}