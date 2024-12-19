package com.alavpa.kakebo.data.mappers

import com.alavpa.kakebo.data.db.entities.LineData
import com.alavpa.kakebo.data.model.CategoryData
import com.alavpa.kakebo.data.model.TypeData
import com.alavpa.kakebo.domain.models.Category
import com.alavpa.kakebo.domain.models.Line
import com.alavpa.kakebo.domain.models.Type
import com.alavpa.kakebo.utils.CalendarUtils
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test

class LineDataMapperTest {

    private val calendarUtils: CalendarUtils = mockk()
    private val categoryDataMapper: CategoryDataMapper = mockk()
    private val typeDataMapper: TypeDataMapper = mockk()
    private val lineDataMapper = LineDataMapper(
        calendarUtils,
        categoryDataMapper,
        typeDataMapper
    )

    @Test
    fun `when map line to data should map properly`() {
        val line = provideLine()
        val linesData = provideLineData(month = 6, year = 2024)
        every { calendarUtils.getYear(any()) } returns 2024
        every { calendarUtils.getMonth(any()) } returns 6
        every { typeDataMapper.from(any()) } returns TypeData.Income
        every { categoryDataMapper.from(any()) } returns CategoryData.Culture

        val result = lineDataMapper.from(line)

        assertEquals(linesData, result)
    }

    @Test
    fun `when map to line should map properly`() {
        val line = provideLine(isFixed = true)
        val lineData = provideLineData(year = 2024, month = 6, isFixed = true)

        every { calendarUtils.getYear(any()) } returns 2024
        every { calendarUtils.getMonth(any()) } returns 6
        every { typeDataMapper.to(any()) } returns Type.Income
        every { categoryDataMapper.to(any()) } returns Category.Culture

        val result = lineDataMapper.to(lineData)

        assertEquals(line, result)
    }

    private fun provideLine(
        id: Long = 0,
        amount: Long = 0,
        description: String = "",
        timestamp: Long = 0,
        type: Type = Type.Income,
        category: Category = Category.Culture,
        isFixed: Boolean = false
    ): Line = Line(id, amount, description, timestamp, type, category, isFixed)

    private fun provideLineData(
        id: Long = 0,
        amount: Long = 0,
        description: String = "",
        timestamp: Long = 0,
        month: Int = 0,
        year: Int = 0,
        type: TypeData = TypeData.Income,
        category: CategoryData = CategoryData.Culture,
        isFixed: Boolean = false
    ): LineData = LineData(id, amount, description, timestamp, month, year, type, category, isFixed)
}
