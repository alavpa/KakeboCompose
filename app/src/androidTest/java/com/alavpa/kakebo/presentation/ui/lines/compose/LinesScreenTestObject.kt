package com.alavpa.kakebo.presentation.ui.lines.compose

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.alavpa.kakebo.presentation.ui.lines.compose.LinesObject.AMOUNT_TEST_TAG
import com.alavpa.kakebo.presentation.ui.lines.compose.LinesObject.DESCRIPTION_TEST_TAG
import com.alavpa.kakebo.presentation.ui.lines.compose.LinesObject.SEND_TEST_TAG

object LinesScreenTestObject {

    fun onWriteAmount(composeTestRule: ComposeTestRule, amount: String) {
        composeTestRule.onNodeWithTag(AMOUNT_TEST_TAG).performTextInput(amount)
    }

    fun onWriteDescription(composeTestRule: ComposeTestRule, amount: String) {
        composeTestRule.onNodeWithTag(DESCRIPTION_TEST_TAG).performTextInput(amount)
    }

    fun onClickSend(composeTestRule: ComposeTestRule) {
        composeTestRule.onNodeWithTag(SEND_TEST_TAG).performClick()
    }

    fun assertAmount(composeTestRule: ComposeTestRule, amount: String) {
        composeTestRule.onNodeWithTag(AMOUNT_TEST_TAG).assert(hasText(amount))
    }

    fun assertDescription(composeTestRule: ComposeTestRule, amount: String) {
        composeTestRule.onNodeWithTag(DESCRIPTION_TEST_TAG).assert(hasText(amount))
    }
}
