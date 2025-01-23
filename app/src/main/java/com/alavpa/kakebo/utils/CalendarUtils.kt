package com.alavpa.kakebo.utils

import android.icu.text.DateFormat
import java.util.Calendar
import javax.inject.Inject

class CalendarUtils @Inject constructor(
    private val calendar: Calendar,
    private val dateFormat: DateFormat
) {
    fun getCurrentTimestamp(): Long {
        return calendar.timeInMillis
    }

    fun getDateFormat(timestamp: Long): String {
        val date = calendar.apply {
            timeInMillis = timestamp
        }.time
        return dateFormat.format(date)
    }

    fun getMonth(timestamp: Long): Int {
        return calendar.apply {
            timeInMillis = timestamp
        }.get(Calendar.MONTH)
    }

    fun getYear(timestamp: Long): Int {
        return calendar.apply { timeInMillis = timestamp }.get(Calendar.YEAR)
    }

    fun getCurrentMonth(): Int = calendar.get(Calendar.MONTH)

    fun getCurrentYear(): Int = calendar.get(Calendar.YEAR)
}
