package com.alavpa.kakebo.data.mappers

import com.alavpa.kakebo.data.db.entities.LineData
import com.alavpa.kakebo.domain.models.Line
import com.alavpa.kakebo.utils.CalendarUtils
import javax.inject.Inject

class LineDataMapper @Inject constructor(
    private val calendarUtils: CalendarUtils,
    private val categoryDataMapper: CategoryDataMapper,
    private val typeDataMapper: TypeDataMapper
) {
    fun from(line: Line): LineData =
        LineData(
            line.id,
            line.amount,
            line.description,
            line.timestamp,
            calendarUtils.getMonth(line.timestamp),
            calendarUtils.getYear(line.timestamp),
            typeDataMapper.from(line.type),
            categoryDataMapper.from(line.category),
            line.isFixed
        )

    fun to(lineData: LineData): Line =
        Line(
            lineData.id,
            lineData.amount,
            lineData.description,
            lineData.timestamp,
            typeDataMapper.to(lineData.type),
            categoryDataMapper.to(lineData.category),
            lineData.isFixed
        )
}
