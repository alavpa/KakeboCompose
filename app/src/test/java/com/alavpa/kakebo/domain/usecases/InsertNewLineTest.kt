package com.alavpa.kakebo.domain.usecases

import com.alavpa.kakebo.domain.KakeboRepository
import com.alavpa.kakebo.domain.models.Category
import com.alavpa.kakebo.domain.models.Line
import com.alavpa.kakebo.domain.models.Type
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class InsertNewLineTest {
    private val repository: KakeboRepository = mockk()
    private val useCase = InsertNewLine(repository)

    @Test
    fun `when call use case should call repository`() = runTest {
        val line = provideLine()
        coEvery { repository.insertNewLine(line) } returns Unit

        useCase(line)

        coVerify { repository.insertNewLine(line) }
    }

    private fun provideLine() = Line(
        amount = 1200,
        description = "description",
        timestamp = 1L,
        category = Category.Leisure,
        type = Type.Income,
        isFixed = false
    )
}