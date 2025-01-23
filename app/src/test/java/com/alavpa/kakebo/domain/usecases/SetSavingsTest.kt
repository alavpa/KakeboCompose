package com.alavpa.kakebo.domain.usecases

import com.alavpa.kakebo.domain.KakeboRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.Test
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest

class SetSavingsTest {
    private val repository: KakeboRepository = mockk()
    private val useCase = SetSavings(repository)

    @Test
    fun `when call use case should call repository`() =
        runTest {
            val savings = "12"
            coEvery { repository.setSavings(savings) } returns flowOf(Result.success(Unit))

            useCase(savings)

            coVerify { repository.setSavings(savings) }
        }

    @Test(expected = IllegalStateException::class)
    fun `when repository throws exception should emit exception`() =
        runTest {
            val savings = "12"
            coEvery { repository.setSavings(savings) } throws IllegalStateException()

            useCase(savings)
        }
}
