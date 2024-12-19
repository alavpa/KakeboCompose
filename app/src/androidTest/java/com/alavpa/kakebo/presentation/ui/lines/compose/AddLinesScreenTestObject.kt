package com.alavpa.kakebo.presentation.ui.lines.compose

import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.alavpa.kakebo.R
import com.alavpa.kakebo.TextUtils

object AddLinesScreenTestObject {

    private fun numberPad(
        composeTestRule: ComposeTestRule,
        number: String
    ): SemanticsNodeInteraction {
        return composeTestRule.onNodeWithText(number)
    }

    private fun keyPad(
        composeTestRule: ComposeTestRule,
        contentDescription: String
    ): SemanticsNodeInteraction {
        return composeTestRule.onNodeWithContentDescription(contentDescription)
    }

    fun onClick1(composeTestRule: ComposeTestRule) {
        numberPad(composeTestRule, "1").performClick()
    }

    fun onClick2(composeTestRule: ComposeTestRule) {
        numberPad(composeTestRule, "2").performClick()
    }

    fun onClickSend(composeTestRule: ComposeTestRule) {
        keyPad(composeTestRule, TextUtils.getText(R.string.send)).performClick()
    }

    fun assertExistsText(composeTestRule: ComposeTestRule, text: String) {
        composeTestRule.onNodeWithText(text).assertExists()
    }
}
