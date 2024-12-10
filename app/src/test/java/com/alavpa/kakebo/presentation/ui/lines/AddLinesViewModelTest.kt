package com.alavpa.kakebo.presentation.ui.lines

import com.alavpa.kakebo.domain.usecases.InsertNewLine
import com.alavpa.kakebo.presentation.mappers.CategoryUIMapper
import com.alavpa.kakebo.presentation.models.CategoryUI
import com.alavpa.kakebo.testutils.MainCoroutinesRule
import com.alavpa.kakebo.utils.AmountUtils
import com.alavpa.kakebo.utils.CalendarUtils
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AddLinesViewModelTest {

    private lateinit var viewModel: AddLinesViewModel

    private val insertNewLine: InsertNewLine = mockk()
    private val calendarUtils: CalendarUtils = mockk()
    private val categoryUIMapper: CategoryUIMapper = mockk()
    private val amountUtils: AmountUtils = mockk()

    @get:Rule
    val coroutineRule = MainCoroutinesRule()

    @Before
    fun setUp() {
        viewModel = AddLinesViewModel(insertNewLine, calendarUtils, categoryUIMapper, amountUtils)
    }

    @Test
    fun `when viewmodel initialize as income should fill categories properly`() {
        val isIncome = true
        val categories = listOf(CategoryUI.Salary, CategoryUI.Gifts, CategoryUI.Extras)
        every { amountUtils.reset() } returns "0.00"

        viewModel.onInitializeOnce(isIncome)

        assertEquals(viewModel.state.value.categories, categories)
    }

    @Test
    fun `when viewmodel initialize as outcome should fill categories properly`() {
        val isIncome = false
        val categories = listOf(
            CategoryUI.Survival,
            CategoryUI.Leisure,
            CategoryUI.Culture,
            CategoryUI.Extras
        )
        every { amountUtils.reset() } returns "0.00"

        viewModel.onInitializeOnce(isIncome)

        assertEquals(viewModel.state.value.categories, categories)
    }

    @Test
    fun `when viewmodel initialize should reset formatted text`() {
        val isIncome = false
        every { amountUtils.reset() } returns "0.00€"

        viewModel.onInitializeOnce(isIncome)

        assertEquals(viewModel.state.value.formattedText, "0.00€")
    }
}