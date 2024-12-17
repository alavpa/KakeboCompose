package com.alavpa.kakebo.presentation.ui.statistics

import com.alavpa.kakebo.domain.models.Category
import com.alavpa.kakebo.domain.models.Line
import com.alavpa.kakebo.domain.models.Type
import com.alavpa.kakebo.domain.usecases.GetAllLines
import com.alavpa.kakebo.domain.usecases.GetSavings
import com.alavpa.kakebo.domain.usecases.SetSavings
import com.alavpa.kakebo.presentation.mappers.LineUIMapper
import com.alavpa.kakebo.presentation.models.LineUI
import com.alavpa.kakebo.testutils.MainCoroutinesRule
import com.alavpa.kakebo.utils.AmountUtils
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlinx.coroutines.flow.flowOf

class StatisticsViewModelTest {
    @get:Rule
    val coroutineRule = MainCoroutinesRule()

    private lateinit var viewmodel: StatisticsViewModel

    private val getAllLines: GetAllLines = mockk()
    private val amountUtils: AmountUtils = mockk()
    private val setSavings: SetSavings = mockk()
    private val getSavings: GetSavings = mockk()
    private val linesUIMapper: LineUIMapper = mockk()

    @Before
    fun setUp() {
        viewmodel = provideViewModel(StatisticsState.INITIAL)
    }

    @Test
    fun `when initialize should set proper values`() {
        val lineMock: LineUI = mockk()
        every { getAllLines() } returns flowOf(getLines())
        every { getSavings() } returns flowOf(1200)
        every { amountUtils.fromLongToCurrency(50000) } returns "$500.00"
        every { amountUtils.fromLongToCurrency(4000) } returns "$40.00"
        every { amountUtils.fromLongToCurrency(46000) } returns "$460.00"
        every { amountUtils.fromLongToCurrency(1200) } returns "$12.00"
        every { amountUtils.fromLongToCurrency(44800) } returns "$448.00"
        every { linesUIMapper.from(any()) } returns lineMock
        val expectedState =
            StatisticsState.INITIAL.copy(
                income = "$500.00",
                outcome = "$40.00",
                budgetText = "$460.00",
                budget = 46000,
                savingsText = "$12.00",
                savings = "12.00",
                budgetWithSavings = "$448.00",
                lines = listOf(lineMock, lineMock)
            )

        viewmodel.onInitializeOnce()

        assertEquals(expectedState, viewmodel.state.value)
    }

    @Test
    fun `when savings change should update value`() {
        every { amountUtils.parseAmountToLong("56") } returns 5600
        every { amountUtils.fromLongToCurrency(5600) } returns "$56.00"
        every { amountUtils.fromLongToCurrency(4400) } returns "$44.00"
        coEvery { setSavings(5600) } just runs
        val viewmodel = provideViewModel(StatisticsState.INITIAL.copy(budget = 10000))
        val expectedState =
            StatisticsState.INITIAL.copy(
                budget = 10000,
                savings = "56",
                savingsText = "$56.00",
                budgetWithSavings = "$44.00"
            )

        viewmodel.onSavingsChanged("56")

        assertEquals(expectedState, viewmodel.state.value)
    }

    private fun provideViewModel(state: StatisticsState) =
        StatisticsViewModel(
            getAllLines,
            amountUtils,
            setSavings,
            getSavings,
            linesUIMapper,
            state
        )

    private fun getLines(): List<Line> {
        return listOf(
            getLine(50000, Type.Income),
            getLine(4000, Type.Outcome)
        )
    }

    private fun getLine(
        amount: Long,
        type: Type
    ): Line {
        return Line(
            amount = amount,
            description = "",
            timestamp = 1,
            type = type,
            category = Category.Extras,
            isFixed = false
        )
    }
}
