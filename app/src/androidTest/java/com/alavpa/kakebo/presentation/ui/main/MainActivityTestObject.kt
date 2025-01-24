package com.alavpa.kakebo.presentation.ui.main

import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import com.alavpa.kakebo.presentation.components.BottomNavItem

object MainActivityTestObject {

    private fun tabIncome(composeTestRule: ComposeTestRule): SemanticsNodeInteraction {
        return composeTestRule.onNodeWithTag(BottomNavItem.Income.route)
    }

    private fun tabStatistics(composeTestRule: ComposeTestRule): SemanticsNodeInteraction {
        return composeTestRule.onNodeWithTag(BottomNavItem.Statistics.route)
    }

    private fun tabOutcome(composeTestRule: ComposeTestRule): SemanticsNodeInteraction {
        return composeTestRule.onNodeWithTag(BottomNavItem.Outcome.route)
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
