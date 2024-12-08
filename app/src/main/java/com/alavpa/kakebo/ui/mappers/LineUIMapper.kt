package com.alavpa.kakebo.ui.mappers

import com.alavpa.kakebo.domain.models.Line
import com.alavpa.kakebo.domain.models.Type
import com.alavpa.kakebo.ui.models.LineUI
import com.alavpa.kakebo.ui.utils.AmountUtils
import com.alavpa.kakebo.ui.utils.CalendarUtils
import javax.inject.Inject

class LineUIMapper @Inject constructor(
    private val calendarUtils: CalendarUtils,
    private val amountUtils: AmountUtils
) {
    fun from(line: Line): LineUI {
        return LineUI(
            amountUtils.fromLongToCurrency(line.amount),
            calendarUtils.getDateFormat(line.timestamp),
            isIncome = line.type == Type.Income
        )
    }
}