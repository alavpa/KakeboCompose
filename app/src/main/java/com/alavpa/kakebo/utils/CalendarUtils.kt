package com.alavpa.kakebo.utils

import android.icu.text.DateFormat
import androidx.annotation.VisibleForTesting
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

class CalendarUtils
@Inject
constructor() {
    fun getCurrentTimestamp(): Long {
        return getCalendarInstance().timeInMillis
    }

    fun getDateFormat(timestamp: Long): String {
        val date =
            getCalendarInstance()
                .apply {
                    timeInMillis = timestamp
                }.time
        return format(date)
    }

    fun getMonth(timestamp: Long): Int {
        return getCalendarInstance().apply {
            timeInMillis = timestamp
        }.get(Calendar.MONTH)
    }

    fun getYear(timestamp: Long): Int {
        return getCalendarInstance().apply {
            timeInMillis = timestamp
        }.get(Calendar.YEAR)
    }

    fun getCurrentMonth(): Int = getCalendarInstance().get(Calendar.MONTH)

    fun getCurrentYear(): Int = getCalendarInstance().get(Calendar.YEAR)

    @VisibleForTesting
    fun getCalendarInstance(): Calendar = Calendar.getInstance()

    @VisibleForTesting
    fun format(date: Date): String = DateFormat.getPatternInstance(DateFormat.YEAR_ABBR_MONTH).format(date)
}
