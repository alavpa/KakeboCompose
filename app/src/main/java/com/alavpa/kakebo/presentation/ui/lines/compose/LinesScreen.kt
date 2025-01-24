package com.alavpa.kakebo.presentation.ui.lines.compose

import MultiPreview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.alavpa.kakebo.R
import com.alavpa.kakebo.presentation.components.DynamicButton
import com.alavpa.kakebo.presentation.components.InitializeOnce
import com.alavpa.kakebo.presentation.components.KTextField
import com.alavpa.kakebo.presentation.components.Pill
import com.alavpa.kakebo.presentation.components.VerticalSpacer
import com.alavpa.kakebo.presentation.theme.KakeboTheme
import com.alavpa.kakebo.presentation.ui.lines.LinesEvent
import com.alavpa.kakebo.presentation.ui.lines.LinesState
import com.alavpa.kakebo.presentation.ui.lines.LinesUserInteractions
import com.alavpa.kakebo.presentation.ui.lines.compose.LinesObject.AMOUNT_TEST_TAG
import com.alavpa.kakebo.presentation.ui.lines.compose.LinesObject.DESCRIPTION_TEST_TAG
import com.alavpa.kakebo.presentation.ui.lines.compose.LinesObject.SEND_TEST_TAG
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow

@Composable
fun LinesScreen(
    state: LinesState,
    event: Flow<LinesEvent>,
    isIncome: Boolean,
    userInteractions: LinesUserInteractions,
    showSnackBarMessage: suspend (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val verticalScrollState = rememberScrollState()
    val color = getColor(isIncome)
    val successMessage = getSuccessMessage(isIncome)

    InitializeOnce { userInteractions.onInitializeOnce(isIncome) }
    LaunchedEffect(Unit) {
        event.collectLatest { event ->
            when (event) {
                LinesEvent.ShowSuccessMessage -> {
                    showSnackBarMessage(successMessage)
                }
            }
        }
    }
    Column(
        Modifier
            .fillMaxSize()
            .padding(
                vertical = KakeboTheme.space.vertical,
                horizontal = KakeboTheme.space.horizontal
            )
            .verticalScroll(verticalScrollState)
    ) {
        Column(Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(stringResource(R.string.repeat_per_month), Modifier.weight(1f))
                Switch(
                    checked = state.repeat,
                    onCheckedChange = { userInteractions.onRepeatChanged(it) },
                    colors = SwitchDefaults.colors(checkedTrackColor = color)
                )
            }
            VerticalSpacer(KakeboTheme.space.m)
            LazyRow(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                items(state.categories) { category ->
                    Pill(
                        text = stringResource(category.resId),
                        isSelected = category == state.selectedCategory,
                        isIncome = isIncome
                    ) {
                        userInteractions.onClickCategory(category)
                    }
                }
            }
            VerticalSpacer(KakeboTheme.space.m)
            KTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(AMOUNT_TEST_TAG),
                isIncome = isIncome,
                value = state.amountText,
                label = if (isIncome) stringResource(R.string.income) else stringResource(R.string.outcome),
                suffix = state.currency,
                onValueChange = { userInteractions.onAmountChanged(it) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                )
            )
            VerticalSpacer(KakeboTheme.space.m)
            KTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(DESCRIPTION_TEST_TAG),
                value = state.description,
                label = stringResource(R.string.concept),
                isIncome = isIncome,
                onValueChange = { userInteractions.onDescriptionChanged(it) },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Send
                ),
                keyboardActions = KeyboardActions(
                    onSend = {
                        focusManager.clearFocus()
                        userInteractions.onClickSend(isIncome)
                    }
                )
            )
        }
        DynamicButton(
            modifier = Modifier
                .fillMaxWidth()
                .testTag(SEND_TEST_TAG),
            text = stringResource(R.string.send),
            isIncome = isIncome,
            onClick = { userInteractions.onClickSend(isIncome) }
        )
    }
}

@Composable
private fun getColor(isIncome: Boolean): Color {
    return if (isIncome) {
        KakeboTheme.colorSchema.incomeColor
    } else {
        KakeboTheme.colorSchema.outcomeColor
    }
}

@Composable
private fun getSuccessMessage(isIncome: Boolean): String {
    return if (isIncome) {
        stringResource(R.string.income_success_message)
    } else {
        stringResource(R.string.outcome_success_message)
    }
}

@Composable
@MultiPreview
private fun LinesScreenPreview() {
    KakeboTheme {
        LinesScreen(
            state = LinesState.INITIAL,
            event = flow {},
            isIncome = false,
            userInteractions = LinesUserInteractions.Stub(),
            showSnackBarMessage = {}
        )
    }
}
