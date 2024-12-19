package com.alavpa.kakebo.presentation.ui.main

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun onInitTabOutcomeIsSelected() {
        MainActivityTestObject.assertTabOutcomeIsSelected(composeRule)
    }

    @Test
    fun onChangeTabToIncome() {
        MainActivityTestObject.onTabIncomePerformClick(composeRule)

        MainActivityTestObject.assertTabIncomeIsSelected(composeRule)
    }

    @Test
    fun onChangeTabToOutcome() {
        MainActivityTestObject.onTabIncomePerformClick(composeRule)
        MainActivityTestObject.onTabOutcomePerformClick(composeRule)

        MainActivityTestObject.assertTabOutcomeIsSelected(composeRule)
    }

    @Test
    fun onChangeTabToStatistics() {
        MainActivityTestObject.onTabStatisticsPerformClick(composeRule)

        MainActivityTestObject.assertTabStatisticsIsSelected(composeRule)
    }
}
