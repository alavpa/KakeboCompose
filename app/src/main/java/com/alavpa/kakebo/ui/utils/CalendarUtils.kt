package com.alavpa.kakebo.ui.utils

import android.icu.text.DateFormat
import java.util.Calendar
import javax.inject.Inject

class CalendarUtils @Inject constructor() {

    fun getCurrentTimestamp(): Long {
        return Calendar.getInstance().timeInMillis
    }

    fun getDateFormat(timestamp: Long): String {
        val date = Calendar.getInstance().apply {
            timeInMillis = timestamp
        }.time
        return DateFormat.getPatternInstance(DateFormat.YEAR_ABBR_MONTH).format(date)
    }
}