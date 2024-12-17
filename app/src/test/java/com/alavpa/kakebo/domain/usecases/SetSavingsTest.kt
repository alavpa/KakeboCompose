package com.alavpa.kakebo.domain.usecases

import com.alavpa.kakebo.domain.KakeboRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import org.junit.Test
import kotlinx.coroutines.test.runTest

class SetSavingsTest {
    private val repository: KakeboRepository = mockk()
    private val useCase = SetSavings(repository)

    @Test
    fun `when call use case should call repository`() =
        runTest {
            val savings: Long = 12
            coEvery { repository.setSavings(savings) } just runs

            useCase(savings)

            coVerify { repository.setSavings(savings) }
        }

    @Test(expected = IllegalStateException::class)
    fun `when repository throws exception should emit exception`() =
        runTest {
            val savings: Long = 12
            coEvery { repository.setSavings(savings) } throws IllegalStateException()

            useCase(savings)
        }
}
