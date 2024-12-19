package com.alavpa.kakebo.presentation.ui.lines.compose

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alavpa.kakebo.R
import com.alavpa.kakebo.TextUtils
import com.alavpa.kakebo.domain.usecases.GetCategories
import com.alavpa.kakebo.domain.usecases.InsertNewLine
import com.alavpa.kakebo.presentation.mappers.CategoryUIMapper
import com.alavpa.kakebo.presentation.theme.KakeboTheme
import com.alavpa.kakebo.presentation.ui.lines.AddLinesState
import com.alavpa.kakebo.presentation.ui.lines.AddLinesViewModel
import com.alavpa.kakebo.utils.AmountUtils
import com.alavpa.kakebo.utils.CalendarUtils
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class AddLinesScreenTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createComposeRule()

    @Inject
    lateinit var insertNewLine: InsertNewLine

    @Inject
    lateinit var getCategories: GetCategories

    @Inject
    lateinit var calendarUtils: CalendarUtils

    @Inject
    lateinit var categoryUIMapper: CategoryUIMapper

    @Inject
    lateinit var amountUtils: AmountUtils

    private lateinit var viewModel: AddLinesViewModel

    @Before
    fun setUp() {
        hiltRule.inject()
        viewModel = AddLinesViewModel(
            insertNewLine,
            getCategories,
            calendarUtils,
            categoryUIMapper,
            amountUtils,
            AddLinesState.INITIAL
        )
    }

    @Test
    fun whenClickNumbersShouldShowAmountCurrency() {
        initializeTest(true) { }

        AddLinesScreenTestObject.onClick1(composeRule)
        AddLinesScreenTestObject.onClick2(composeRule)

        AddLinesScreenTestObject.assertExistsText(composeRule, "$0.12")
    }

    @Test
    fun whenSendOutcomeShouldShowSnackBar() {
        var showSnackBarMessage = false
        val expectedMessage = TextUtils.getText(R.string.outcome_success_message)
        initializeTest(false) { text ->
            showSnackBarMessage = true
            assertEquals(expectedMessage, text)
        }

        AddLinesScreenTestObject.onClick1(composeRule)
        AddLinesScreenTestObject.onClick2(composeRule)
        AddLinesScreenTestObject.onClickSend(composeRule)

        AddLinesScreenTestObject.assertExistsText(composeRule, "$0.00")
        assertTrue(showSnackBarMessage)
    }

    @Test
    fun whenSendIncomeShouldShowSnackBar() {
        var showSnackBarMessage = false
        val expectedMessage = TextUtils.getText(R.string.income_success_message)
        initializeTest(true) { text ->
            showSnackBarMessage = true
            assertEquals(expectedMessage, text)
        }

        AddLinesScreenTestObject.onClick1(composeRule)
        AddLinesScreenTestObject.onClick2(composeRule)
        AddLinesScreenTestObject.onClickSend(composeRule)

        AddLinesScreenTestObject.assertExistsText(composeRule, "$0.00")
        assertTrue(showSnackBarMessage)
    }

    private fun initializeTest(isIncome: Boolean, showSnackBarMessage: (String) -> Unit) {
        composeRule.setContent {
            KakeboTheme {
                AddLinesScreen(
                    viewModel.state.collectAsState().value,
                    isIncome,
                    viewModel,
                    showSnackBarMessage
                )
            }
        }
    }
}
