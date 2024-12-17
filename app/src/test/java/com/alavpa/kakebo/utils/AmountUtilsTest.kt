package com.alavpa.kakebo.utils

import io.mockk.every
import io.mockk.spyk
import org.junit.Assert.assertEquals
import org.junit.Test

class AmountUtilsTest {
    private val amountUtils: AmountUtils = spyk()

    @Test
    fun `when format from long should return proper currency`() {
        every { amountUtils.format(12.0) } returns "$12.00"

        val currency = amountUtils.fromLongToCurrency(1200)

        assertEquals("$12.00", currency)
    }

    @Test
    fun `when format from text should return proper currency`() {
        every { amountUtils.format(12.0) } returns "$12.00"

        val currency = amountUtils.fromTextToCurrency("1200")

        assertEquals("$12.00", currency)
    }

    @Test
    fun `when reset should return proper currency`() {
        every { amountUtils.format(0.0) } returns "$0.00"

        val currency = amountUtils.reset()

        assertEquals("$0.00", currency)
    }

    @Test
    fun `when parse from amount should return proper Long value`() {
        every { amountUtils.parse("12.45") } returns 12.45

        val value = amountUtils.parseAmountToLong("12.45")

        assertEquals(1245, value)
    }
}
