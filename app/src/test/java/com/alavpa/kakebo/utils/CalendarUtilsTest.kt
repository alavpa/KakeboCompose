package com.alavpa.kakebo.utils

import io.mockk.every
import io.mockk.spyk
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Calendar

private const val TIMESTAMP: Long = 1734036363000

class CalendarUtilsTest {
    private val calendarUtils: CalendarUtils = spyk()

    @Test
    fun `when ask for current month return current month`() {
        every { calendarUtils.getCalendarInstance() } returns
            Calendar.getInstance().apply {
                set(Calendar.MONTH, 10)
            }

        val currentMonth = calendarUtils.getCurrentMonth()

        assertEquals(10, currentMonth)
    }

    @Test
    fun `when ask for current timestamp return current timestamp`() {
        every { calendarUtils.getCalendarInstance() } returns
            Calendar.getInstance().apply {
                timeInMillis = 10
            }

        val currentMonth = calendarUtils.getCurrentTimestamp()

        assertEquals(10, currentMonth)
    }

    @Test
    fun `when ask for current year return current year`() {
        every { calendarUtils.getCalendarInstance() } returns
            Calendar.getInstance()
                .apply {
                    set(Calendar.YEAR, 1089)
                }

        val currentMonth = calendarUtils.getCurrentYear()

        assertEquals(1089, currentMonth)
    }

    @Test
    fun `when ask for year by timestamp return proper year`() {
        val year = calendarUtils.getYear(TIMESTAMP)

        assertEquals(2024, year)
    }

    @Test
    fun `when ask for month by timestamp return proper month`() {
        val month = calendarUtils.getMonth(TIMESTAMP)

        assertEquals(11, month)
    }

    @Test
    fun `when ask for current date formatted by timestamp return proper date`() {
        val expectedDate = Calendar.getInstance().apply { timeInMillis = TIMESTAMP }.time
        every { calendarUtils.format(expectedDate) } returns "Dec 2024"

        val date = calendarUtils.getDateFormat(TIMESTAMP)

        assertEquals("Dec 2024", date)
    }
}
