package com.alavpa.kakebo.presentation.mappers

import com.alavpa.kakebo.domain.models.Line
import com.alavpa.kakebo.domain.models.Type
import com.alavpa.kakebo.presentation.models.LineUI
import com.alavpa.kakebo.utils.CalendarUtils
import javax.inject.Inject

class LineUIMapper @Inject constructor(
    private val categoryUIMapper: CategoryUIMapper,
    private val calendarUtils: CalendarUtils
) {
    fun from(line: Line): LineUI {
        return LineUI(
            id = line.id,
            amount = line.amount.toString(),
            date = calendarUtils.getDateFormat(line.timestamp),
            isIncome = line.type == Type.Income,
            repeatPerMonth = line.repeat,
            category = categoryUIMapper.from(line.category),
            description = line.description
        )
    }
}
