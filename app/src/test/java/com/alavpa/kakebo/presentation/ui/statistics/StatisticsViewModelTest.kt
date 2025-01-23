package com.alavpa.kakebo.presentation.ui.statistics

import com.alavpa.kakebo.domain.models.Category
import com.alavpa.kakebo.domain.models.Line
import com.alavpa.kakebo.domain.models.Type
import com.alavpa.kakebo.domain.usecases.GetAllLines
import com.alavpa.kakebo.domain.usecases.GetSavings
import com.alavpa.kakebo.domain.usecases.RemoveLine
import com.alavpa.kakebo.domain.usecases.SetSavings
import com.alavpa.kakebo.presentation.mappers.LineUIMapper
import com.alavpa.kakebo.presentation.models.LineUI
import com.alavpa.kakebo.testutils.MainCoroutinesRule
import io.mockk.called
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlinx.coroutines.flow.flowOf

@RunWith(RobolectricTestRunner::class)
class StatisticsViewModelTest {
    private lateinit var viewmodel: StatisticsViewModel

    private val getAllLines: GetAllLines = mockk()
    private val setSavings: SetSavings = mockk()
    private val getSavings: GetSavings = mockk()
    private val removeLine: RemoveLine = mockk()
    private val linesUIMapper: LineUIMapper = mockk()

    @get:Rule
    val coroutineRule = MainCoroutinesRule()

    @Before
    fun setUp() {
        viewmodel = provideViewModel(StatisticsState.INITIAL)
    }

    @Test
    fun `when initialize should set proper values`() {
        val lineMock: LineUI = mockk()
        every { getAllLines() } returns flowOf(Result.success(getLines()))
        every { getSavings() } returns flowOf(Result.success("12"))
        every { linesUIMapper.from(any()) } returns lineMock
        val expectedState = StatisticsState.INITIAL.copy(
            income = "500",
            outcome = "40",
            budgetText = "460",
            budget = 460,
            savings = "12",
            savingsText = "12",
            budgetWithSavings = "448",
            lines = listOf(lineMock, lineMock)
        )

        viewmodel.onInitializeOnce()

        assertEquals(expectedState, viewmodel.state.value)
    }

    @Test
    fun `when savings change should update value`() {
        coEvery { setSavings("56") } returns flowOf(Result.success(Unit))
        val viewmodel = provideViewModel(StatisticsState.INITIAL)
        val expectedState = StatisticsState.INITIAL.copy(savings = "56")

        viewmodel.onSavingsChanged("56")

        assertEquals(expectedState, viewmodel.state.value)
        verify { setSavings("56") }
    }

    @Test
    fun `when savings wrong value should not do anything`() {
        val viewmodel = provideViewModel(StatisticsState.INITIAL.copy(savings = "45"))
        val expectedState = StatisticsState.INITIAL.copy(savings = "45")

        viewmodel.onSavingsChanged("text")

        assertEquals(expectedState, viewmodel.state.value)
        verify { setSavings wasNot called }
    }

    private fun provideViewModel(state: StatisticsState) =
        StatisticsViewModel(
            getAllLines,
            setSavings,
            getSavings,
            removeLine,
            linesUIMapper,
            state
        )

    private fun getLines(): List<Line> {
        return listOf(
            getLine(500, Type.Income),
            getLine(40, Type.Outcome)
        )
    }

    private fun getLine(
        amount: Int,
        type: Type
    ): Line {
        return Line(
            amount = amount,
            description = "",
            timestamp = 1,
            type = type,
            category = Category.Extras,
            repeat = false
        )
    }
}
