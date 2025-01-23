package com.alavpa.kakebo.utils

import android.icu.text.DateFormat
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.Calendar

private const val TIMESTAMP: Long = 1734036363000

class CalendarUtilsTest {

    private val calendar = Calendar.getInstance()
    private val dateFormat: DateFormat = mockk()
    private val calendarUtils = CalendarUtils(calendar, dateFormat)

    @Before
    fun setUp() {
        calendar.clear()
    }

    @Test
    fun `when ask for current month return current month`() {
        calendar.set(Calendar.MONTH, 10)

        val currentMonth = calendarUtils.getCurrentMonth()

        assertEquals(10, currentMonth)
    }

    @Test
    fun `when ask for current timestamp return current timestamp`() {
        calendar.timeInMillis = TIMESTAMP

        val currentTimeStamp = calendarUtils.getCurrentTimestamp()

        assertEquals(TIMESTAMP, currentTimeStamp)
    }

    @Test
    fun `when ask for current year return current year`() {
        calendar.set(Calendar.YEAR, 1089)

        val currentYear = calendarUtils.getCurrentYear()

        assertEquals(1089, currentYear)
    }

    @Test
    fun `when ask for year by timestamp return proper year`() {
        calendar.timeInMillis = TIMESTAMP

        val year = calendarUtils.getYear(TIMESTAMP)

        assertEquals(2024, year)
    }

    @Test
    fun `when ask for month by timestamp return proper month`() {
        calendar.timeInMillis = TIMESTAMP

        val month = calendarUtils.getMonth(TIMESTAMP)

        assertEquals(11, month)
    }

    @Test
    fun `when ask for current date formatted by timestamp return proper date`() {
        calendar.timeInMillis = TIMESTAMP
        val expectedDate = calendar.time
        every {dateFormat.format(expectedDate) } returns "Dec 2024"

        val date = calendarUtils.getDateFormat(TIMESTAMP)

        assertEquals("Dec 2024", date)
    }
}
