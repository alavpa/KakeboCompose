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
import com.alavpa.kakebo.presentation.ui.lines.LinesState
import com.alavpa.kakebo.presentation.ui.lines.LinesViewModel
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
class LinesScreenTest {
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

    private lateinit var viewModel: LinesViewModel

    @Before
    fun setUp() {
        hiltRule.inject()
        viewModel = LinesViewModel(
            insertNewLine,
            getCategories,
            calendarUtils,
            categoryUIMapper,
            LinesState.INITIAL
        )
    }

    @Test
    fun whenClickNumbersShouldShowAmount() {
        initializeTest(true) { }

        LinesScreenTestObject.onWriteAmount(composeRule, "12")
        LinesScreenTestObject.onWriteAmount(composeRule, "24")

        LinesScreenTestObject.assertAmount(composeRule, "1224")
    }

    @Test
    fun whenClickNotNumbersShouldNotShowAmount() {
        initializeTest(true) { }

        LinesScreenTestObject.onWriteAmount(composeRule, "12")
        LinesScreenTestObject.onWriteAmount(composeRule, ",12")

        LinesScreenTestObject.assertAmount(composeRule, "12")
    }

    @Test
    fun whenSendOutcomeShouldShowSnackBar() {
        var showSnackBarMessage = false
        val expectedMessage = TextUtils.getText(R.string.outcome_success_message)
        initializeTest(false) { text ->
            showSnackBarMessage = true
            assertEquals(expectedMessage, text)
        }

        LinesScreenTestObject.onWriteAmount(composeRule, "1234")
        LinesScreenTestObject.onWriteDescription(composeRule, "description")
        LinesScreenTestObject.onClickSend(composeRule)

        LinesScreenTestObject.assertAmount(composeRule, "")
        LinesScreenTestObject.assertDescription(composeRule, "")
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

        LinesScreenTestObject.onWriteAmount(composeRule, "1234")
        LinesScreenTestObject.onWriteDescription(composeRule, "description")
        LinesScreenTestObject.onClickSend(composeRule)

        LinesScreenTestObject.assertAmount(composeRule, "")
        LinesScreenTestObject.assertDescription(composeRule, "")
        assertTrue(showSnackBarMessage)
    }

    private fun initializeTest(isIncome: Boolean, showSnackBarMessage: (String) -> Unit) {
        composeRule.setContent {
            KakeboTheme {
                LinesScreen(
                    viewModel.state.collectAsState().value,
                    viewModel.eventsFlow,
                    isIncome,
                    viewModel,
                    showSnackBarMessage
                )
            }
        }
    }
}
