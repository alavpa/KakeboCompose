package com.alavpa.kakebo.domain.usecases

import app.cash.turbine.test
import com.alavpa.kakebo.domain.KakeboRepository
import com.alavpa.kakebo.domain.models.Line
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetAllLinesTest {

    private val repository: KakeboRepository = mockk()
    private val useCase = GetAllLines(repository)

    @Test
    fun `when call use case should call repository`() = runTest {
        every { repository.getAllLines() } returns flowOf(emptyList())

        useCase().test {
            assertEquals(emptyList<Line>(), awaitItem())
            verify { repository.getAllLines() }
            awaitComplete()
        }
    }

    @Test
    fun `when repository throw exception should emit exception`() = runTest {
        every { repository.getAllLines() } returns flow { throw IllegalStateException() }

        useCase().test {
            awaitError()
        }
    }
}