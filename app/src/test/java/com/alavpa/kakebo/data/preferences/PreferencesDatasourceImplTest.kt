package com.alavpa.kakebo.data.preferences

import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest

class PreferencesDatasourceImplTest {
    private val dataStore: KakeboDataStore = mockk()
    private val preferencesDatasourceImpl = PreferencesDatasourceImpl(dataStore)

    @Test
    fun `when set savings should verify call to data store properly`() =
        runTest {
            coEvery { dataStore.save(any()) } just runs

            preferencesDatasourceImpl.setSavings(1200)

            coVerify { dataStore.save(1200) }
        }

    @Test
    fun `when get savings should verify flow get properly`() =
        runTest {
            every { dataStore.savingsFlow() } returns flowOf(1200)

            preferencesDatasourceImpl.getSavings().test {
                assertEquals(1200, awaitItem())
                awaitComplete()
                verify { dataStore.savingsFlow() }
            }
        }
}
