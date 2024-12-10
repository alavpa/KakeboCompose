package com.alavpa.kakebo.domain.usecases

import app.cash.turbine.test
import com.alavpa.kakebo.domain.KakeboRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetSavingsTest {
    private val repository: KakeboRepository = mockk()
    private val useCase = GetSavings(repository)

    @Test
    fun `when call use case should call repository`() = runTest {
        every { repository.getSavings() } returns flowOf(1200)

        useCase().test {
            assertEquals(1200, awaitItem())
            verify { repository.getSavings() }
            awaitComplete()
        }
    }

    @Test
    fun `when repository launch exception should emit exception`() = runTest {
        every { repository.getSavings() } returns flow { throw IllegalStateException() }

        useCase().test {
            awaitError()
        }
    }
}