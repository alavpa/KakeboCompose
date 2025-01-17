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
import com.alavpa.kakebo.utils.AmountUtils
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
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest

class AddLinesViewModelTest {
    private lateinit var viewModel: AddLinesViewModel

    private val insertNewLine: InsertNewLine = mockk()
    private val calendarUtils: CalendarUtils = mockk()
    private val categoryUIMapper = CategoryUIMapper()
    private val amountUtils: AmountUtils = mockk()
    private val getCategories: GetCategories = mockk()

    @get:Rule
    val coroutineRule = MainCoroutinesRule()

    @Before
    fun setUp() {
        viewModel = provideViewModel(AddLinesState.INITIAL)
    }

    @Test
    fun `when viewmodel initialize as income should fill categories properly`() {
        val isIncome = true
        val categories = listOf(Category.Salary)
        val expectedCategories = listOf(CategoryUI.Salary)
        every { getCategories(isIncome) } returns flowOf(Result.success(categories))
        every { amountUtils.fromTextToCurrency(any()) } returns "0.00"

        viewModel.onInitializeOnce(isIncome)

        assertEquals(viewModel.state.value.categories, expectedCategories)
    }

    @Test
    fun `when viewmodel initialize as outcome should call get categories`() {
        val isIncome = false
        every { getCategories(isIncome) } returns flowOf(Result.success(listOf(Category.Salary)))
        every { amountUtils.fromTextToCurrency(any()) } returns "0.00"

        viewModel.onInitializeOnce(isIncome)

        verify { getCategories(isIncome) }
    }

    @Test
    fun `when viewmodel initialize should reset formatted text`() {
        val isIncome = false
        every { getCategories(isIncome) } returns flowOf(Result.success(listOf(Category.Salary)))
        every { amountUtils.fromTextToCurrency(any()) } returns "0.00€"

        viewModel.onInitializeOnce(isIncome)

        assertEquals(viewModel.state.value.formattedText, "0.00€")
    }

    @Test
    fun `when viewmodel click number should call format and update current text`() {
        every { amountUtils.fromTextToCurrency("4") } returns "$0.04"
        val expectedState =
            AddLinesState.INITIAL
                .copy(
                    currentText = "4",
                    formattedText = "$0.04"
                )

        viewModel.onClickNumber("4")

        verify { amountUtils.fromTextToCurrency(any()) }
        assertEquals(expectedState, viewModel.state.value)
    }

    @Test
    fun `when viewmodel click number should add new number at the end of current text`() {
        every { amountUtils.fromTextToCurrency("54") } returns "$0.54"
        val expectedState =
            AddLinesState.INITIAL
                .copy(
                    currentText = "54",
                    formattedText = "$0.54"
                )
        val viewModel = provideViewModel(AddLinesState.INITIAL.copy(currentText = "5"))

        viewModel.onClickNumber("4")

        verify { amountUtils.fromTextToCurrency(any()) }
        assertEquals(expectedState, viewModel.state.value)
    }

    @Test
    fun `when current text is greater than 8 should do nothing`() {
        val expectedState = AddLinesState.INITIAL.copy(currentText = "12345678")
        val viewModel = provideViewModel(AddLinesState.INITIAL.copy(currentText = "12345678"))

        viewModel.onClickNumber("9")

        verify(exactly = 0) { amountUtils.fromTextToCurrency(any()) }
        assertEquals(expectedState, viewModel.state.value)
    }

    @Test
    fun `when click on Delete should remove one letter`() {
        val expectedState = AddLinesState.INITIAL.copy(currentText = "1234")
        val viewModel = provideViewModel(AddLinesState.INITIAL.copy(currentText = "12345"))
        every { amountUtils.fromTextToCurrency(any()) } returns ""

        viewModel.onClickDelete()

        assertEquals(expectedState, viewModel.state.value)
    }

    @Test
    fun `when click on Delete and currentText is empty should do nothing`() {
        val expectedState = AddLinesState.INITIAL.copy(currentText = "")

        viewModel.onClickDelete()

        assertEquals(expectedState, viewModel.state.value)
    }

    @Test
    fun `when click on OK and is income should send income, reset and show success message`() =
        runTest {
            every { calendarUtils.getCurrentTimestamp() } returns 1
            every { amountUtils.reset() } returns ""
            coEvery { insertNewLine(any()) } returns flowOf(Result.success(Unit))
            val isIncome = true
            val expectedState = AddLinesState.INITIAL.copy(
                selectedCategory = CategoryUI.Salary,
                isFixed = true
            )
            val expectedLine = Line(
                amount = 1234,
                description = "description",
                timestamp = 1,
                type = Type.Income,
                category = Category.Salary,
                isFixed = true
            )
            val viewModel = provideViewModel(
                currentState = AddLinesState.INITIAL.copy(
                    currentText = "1234",
                    description = "description",
                    selectedCategory = CategoryUI.Salary,
                    isFixed = true
                )
            )

            viewModel.onClickOk(isIncome)

            viewModel.eventsFlow.test {
                assertEquals(awaitItem(), AddLinesEvent.ShowSuccessMessage)
            }
            coVerify { insertNewLine(expectedLine) }
            assertEquals(expectedState, viewModel.state.value)
        }

    @Test
    fun `when description change should overwrite state description`() {
        val expectedEstate = AddLinesState.INITIAL.copy(description = "Hello World!")

        viewModel.onDescriptionChanged("Hello World!")

        assertEquals(expectedEstate, viewModel.state.value)
    }

    @Test
    fun `when category is clicked should select category`() {
        val expectedEstate = AddLinesState.INITIAL.copy(selectedCategory = CategoryUI.Culture)

        viewModel.onClickCategory(CategoryUI.Culture)

        assertEquals(expectedEstate, viewModel.state.value)
    }

    @Test
    fun `when category is clicked 2 times should clear category`() {
        val expectedEstate = AddLinesState.INITIAL.copy(selectedCategory = null)

        viewModel.onClickCategory(CategoryUI.Culture)
        viewModel.onClickCategory(CategoryUI.Culture)

        assertEquals(expectedEstate, viewModel.state.value)
    }

    @Test
    fun `when isFixed amount is true should save proper value`() {
        val expectedEstate = AddLinesState.INITIAL.copy(isFixed = true)

        viewModel.onIsFixedOutcomeChanged(true)

        assertEquals(expectedEstate, viewModel.state.value)
    }

    @Test
    fun `when isFixed amount is false should save proper value`() {
        val expectedEstate = AddLinesState.INITIAL.copy(isFixed = false)
        val viewModel = provideViewModel(AddLinesState.INITIAL.copy(isFixed = true))

        viewModel.onIsFixedOutcomeChanged(false)

        assertEquals(expectedEstate, viewModel.state.value)
    }

    private fun provideViewModel(currentState: AddLinesState) =
        AddLinesViewModel(
            insertNewLine,
            getCategories,
            calendarUtils,
            categoryUIMapper,
            amountUtils,
            currentState
        )
}
