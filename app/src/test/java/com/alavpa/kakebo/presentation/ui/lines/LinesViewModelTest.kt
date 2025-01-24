package com.alavpa.kakebo.presentation.ui.lines

import app.cash.turbine.test
import com.alavpa.kakebo.domain.models.Category
import com.alavpa.kakebo.domain.models.Line
import com.alavpa.kakebo.domain.models.Type
import com.alavpa.kakebo.domain.usecases.GetCategories
import com.alavpa.kakebo.domain.usecases.InsertNewLine
import com.alavpa.kakebo.presentation.mappers.CategoryUIMapper
import com.alavpa.kakebo.presentation.models.CategoryUI
import com.alavpa.kakebo.testutils.MainCoroutinesRule
import com.alavpa.kakebo.utils.CalendarUtils
import io.mockk.coEvery
import io.mockk.coVerify
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
import kotlinx.coroutines.test.runTest

@RunWith(RobolectricTestRunner::class)
class LinesViewModelTest {
    private lateinit var viewModel: LinesViewModel

    private val insertNewLine: InsertNewLine = mockk()
    private val calendarUtils: CalendarUtils = mockk()
    private val categoryUIMapper = CategoryUIMapper()
    private val getCategories: GetCategories = mockk()

    @get:Rule
    val coroutineRule = MainCoroutinesRule()

    @Before
    fun setUp() {
        viewModel = provideViewModel(LinesState.INITIAL)
    }

    @Test
    fun `when viewmodel initialize as income should fill categories properly`() {
        val isIncome = true
        val categories = listOf(Category.Salary)
        every { getCategories(isIncome) } returns flowOf(Result.success(categories))
        val expectedState = LinesState.INITIAL.copy(
            categories = listOf(CategoryUI.Salary),
            currency = "$"
        )

        viewModel.onInitializeOnce(isIncome)

        assertEquals(viewModel.state.value, expectedState)
    }

    @Test
    fun `when viewmodel initialize as outcome should call get categories`() {
        val isIncome = false
        every { getCategories(isIncome) } returns flowOf(Result.success(listOf(Category.Salary)))

        viewModel.onInitializeOnce(isIncome)

        verify { getCategories(isIncome) }
    }

    @Test
    fun `when viewmodel initialize should reset formatted text`() {
        val isIncome = false
        every { getCategories(isIncome) } returns flowOf(Result.success(listOf(Category.Salary)))

        viewModel.onInitializeOnce(isIncome)

        assertEquals(viewModel.state.value.amountText, "")
    }

    @Test
    fun `when click on Send and is income should send income, reset and show success message`() =
        runTest {
            every { calendarUtils.getCurrentTimestamp() } returns 1
            coEvery { insertNewLine(any()) } returns flowOf(Result.success(Unit))
            val isIncome = true
            val expectedState = LinesState.INITIAL.copy(
                selectedCategory = CategoryUI.Salary,
                repeat = true
            )
            val expectedLine = Line(
                amount = 1234,
                description = "description",
                timestamp = 1,
                type = Type.Income,
                category = Category.Salary,
                repeat = true
            )
            val viewModel = provideViewModel(
                currentState = LinesState.INITIAL.copy(
                    amountText = "1234",
                    description = "description",
                    selectedCategory = CategoryUI.Salary,
                    repeat = true
                )
            )

            viewModel.onClickSend(isIncome)

            viewModel.eventsFlow.test {
                assertEquals(awaitItem(), LinesEvent.ShowSuccessMessage)
            }
            coVerify { insertNewLine(expectedLine) }
            assertEquals(expectedState, viewModel.state.value)
        }

    @Test
    fun `when description change should overwrite state description`() {
        val expectedEstate = LinesState.INITIAL.copy(description = "Hello World!")

        viewModel.onDescriptionChanged("Hello World!")

        assertEquals(expectedEstate, viewModel.state.value)
    }

    @Test
    fun `when category is clicked should select category`() {
        val expectedEstate = LinesState.INITIAL.copy(selectedCategory = CategoryUI.Culture)

        viewModel.onClickCategory(CategoryUI.Culture)

        assertEquals(expectedEstate, viewModel.state.value)
    }

    @Test
    fun `when category is clicked 2 times should clear category`() {
        val expectedEstate = LinesState.INITIAL.copy(selectedCategory = null)

        viewModel.onClickCategory(CategoryUI.Culture)
        viewModel.onClickCategory(CategoryUI.Culture)

        assertEquals(expectedEstate, viewModel.state.value)
    }

    @Test
    fun `when repeat amount is true should save proper value`() {
        val expectedEstate = LinesState.INITIAL.copy(repeat = true)

        viewModel.onRepeatChanged(true)

        assertEquals(expectedEstate, viewModel.state.value)
    }

    @Test
    fun `when repeat amount is false should save proper value`() {
        val expectedEstate = LinesState.INITIAL.copy(repeat = false)
        val viewModel = provideViewModel(LinesState.INITIAL.copy(repeat = true))

        viewModel.onRepeatChanged(false)

        assertEquals(expectedEstate, viewModel.state.value)
    }

    @Test
    fun `when change amount should save proper value`() {
        val expectedEstate = LinesState.INITIAL.copy(amountText = "456")
        val viewModel = provideViewModel(LinesState.INITIAL.copy(amountText = ""))

        viewModel.onAmountChanged("456")

        assertEquals(expectedEstate, viewModel.state.value)
    }

    @Test
    fun `when change wrong amount should do nothing`() {
        val expectedEstate = LinesState.INITIAL.copy(amountText = "")
        val viewModel = provideViewModel(LinesState.INITIAL.copy(amountText = ""))

        viewModel.onAmountChanged("text")

        assertEquals(expectedEstate, viewModel.state.value)
    }

    private fun provideViewModel(currentState: LinesState) = LinesViewModel(
        insertNewLine,
        getCategories,
        calendarUtils,
        categoryUIMapper,
        currentState
    )
}
