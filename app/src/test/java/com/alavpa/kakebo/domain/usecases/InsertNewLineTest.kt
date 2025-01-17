package com.alavpa.kakebo.domain.usecases

import com.alavpa.kakebo.domain.KakeboRepository
import com.alavpa.kakebo.domain.models.Line
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.Test
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest

class InsertNewLineTest {
    private val repository: KakeboRepository = mockk()
    private val useCase = InsertNewLine(repository)

    @Test
    fun `when call use case should call repository`() =
        runTest {
            val line: Line = mockk()
            coEvery { repository.insertNewLine(line) } returns flowOf(Result.success(Unit))

            useCase(line)

            coVerify { repository.insertNewLine(line) }
        }

    @Test(expected = IllegalStateException::class)
    fun `when repository throws exception should emit exception`() =
        runTest {
            coEvery { repository.insertNewLine(any()) } throws IllegalStateException()

            useCase(mockk())
        }
}
