package com.alavpa.kakebo.domain.usecases

import com.alavpa.kakebo.domain.KakeboRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class SetSavingsTest {
    private val repository: KakeboRepository = mockk()
    private val useCase = SetSavings(repository)

    @Test
    fun `when call use case should call repository`() = runTest {
        coEvery { repository.setSavings(1200) } returns Unit

        useCase(1200)

        coVerify { repository.setSavings(1200) }
    }
}