package com.alavpa.kakebo.presentation.mappers

import com.alavpa.kakebo.domain.models.Line
import com.alavpa.kakebo.domain.models.Type
import com.alavpa.kakebo.presentation.models.LineUI
import com.alavpa.kakebo.utils.AmountUtils
import com.alavpa.kakebo.utils.CalendarUtils
import javax.inject.Inject

class LineUIMapper @Inject constructor(
    private val categoryUIMapper: CategoryUIMapper,
    private val calendarUtils: CalendarUtils,
    private val amountUtils: AmountUtils
) {
    fun from(line: Line): LineUI {
        return LineUI(
            amountUtils.fromLongToCurrency(line.amount),
            calendarUtils.getDateFormat(line.timestamp),
            isIncome = line.type == Type.Income,
            repeatPerMonth = line.isFixed,
            category = categoryUIMapper.from(line.category),
            description = line.description
        )
    }
}
