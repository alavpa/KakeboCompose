package com.alavpa.kakebo.presentation.ui.main

import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import com.alavpa.kakebo.R
import com.alavpa.kakebo.TextUtils

object MainActivityTestObject {

    private fun tabIncome(composeTestRule: ComposeTestRule): SemanticsNodeInteraction {
        return composeTestRule.onNodeWithText(TextUtils.getText(R.string.income))
    }

    private fun tabStatistics(composeTestRule: ComposeTestRule): SemanticsNodeInteraction {
        return composeTestRule.onNodeWithText(TextUtils.getText(R.string.statistics))
    }

    private fun tabOutcome(composeTestRule: ComposeTestRule): SemanticsNodeInteraction {
        return composeTestRule.onNodeWithText(TextUtils.getText(R.string.outcome))
    }

    fun onTabIncomePerformClick(composeTestRule: ComposeTestRule): SemanticsNodeInteraction {
        return tabIncome(composeTestRule).performClick()
    }

    fun onTabOutcomePerformClick(composeTestRule: ComposeTestRule): SemanticsNodeInteraction {
        return tabOutcome(composeTestRule).performClick()
    }

    fun onTabStatisticsPerformClick(composeTestRule: ComposeTestRule): SemanticsNodeInteraction {
        return tabStatistics(composeTestRule).performClick()
    }

    fun assertTabStatisticsIsSelected(composeTestRule: ComposeTestRule) {
        val array = IntArray(20)
        composeTestRule.onRoot().captureToImage().readPixels(array, 0, 0, 5, 4)
    }

    fun assertTabIncomeIsSelected(composeTestRule: ComposeTestRule) {
        tabIncome(composeTestRule).assertIsSelected()
    }

    fun assertTabOutcomeIsSelected(composeTestRule: ComposeTestRule) {
        tabOutcome(composeTestRule).assertIsSelected()
    }
}
