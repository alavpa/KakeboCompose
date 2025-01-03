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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import com.alavpa.kakebo.R
import com.alavpa.kakebo.presentation.components.CategoryPill
import com.alavpa.kakebo.presentation.components.HorizontalSpacer
import com.alavpa.kakebo.presentation.components.InitializeOnce
import com.alavpa.kakebo.presentation.components.Pad
import com.alavpa.kakebo.presentation.components.VerticalSpacer
import com.alavpa.kakebo.presentation.theme.KakeboTheme
import com.alavpa.kakebo.presentation.ui.lines.AddLinesState
import com.alavpa.kakebo.presentation.ui.lines.AddLinesUserInteractions

@Composable
fun AddLinesScreen(
    state: AddLinesState,
    isIncome: Boolean,
    userInteractions: AddLinesUserInteractions,
    showSnackBarMessage: suspend (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val color =
        if (isIncome) {
            KakeboTheme.colorSchema.incomeColor
        } else {
            KakeboTheme.colorSchema.outcomeColor
        }
    val verticalScrollState = rememberScrollState()
    InitializeOnce { userInteractions.onInitializeOnce(isIncome) }
    val successMessage =
        if (isIncome) {
            stringResource(R.string.income_success_message)
        } else {
            stringResource(R.string.outcome_success_message)
        }
    LaunchedEffect(state.showSuccess) {
        if (state.showSuccess) {
            showSnackBarMessage(successMessage)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(KakeboTheme.space.horizontal)
            .verticalScroll(verticalScrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = state.isFixed,
                onCheckedChange = { isFixed ->
                    userInteractions.onIsFixedOutcomeChanged(isFixed)
                },
                colors =
                CheckboxDefaults.colors().copy(
                    checkedBoxColor = color,
                    uncheckedBorderColor = color,
                    checkedBorderColor = color
                )
            )
            Text(
                stringResource(R.string.repeat_per_month),
                color = color
            )
        }

        VerticalSpacer(height = KakeboTheme.space.vertical)
        LazyRow(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            items(state.categories) { category ->
                CategoryPill(
                    text = stringResource(category.resId),
                    isIncome = isIncome,
                    isSelected = category == state.selectedCategory
                ) {
                    userInteractions.onClickCategory(category)
                }
            }
        }
        VerticalSpacer(height = KakeboTheme.space.vertical)
        Text(text = state.formattedText, style = KakeboTheme.typography.padText)
        Pad(userInteractions, isIncome)
        VerticalSpacer(height = KakeboTheme.space.vertical)
        Row {
            TextField(
                value = state.description,
                onValueChange = { userInteractions.onDescriptionChanged(it) },
                label = { Text(stringResource(R.string.concept)) },
                modifier = Modifier.weight(1f, fill = true)
            )
            HorizontalSpacer(KakeboTheme.space.horizontal)
            FloatingActionButton(
                onClick = {
                    focusManager.clearFocus()
                    userInteractions.onClickOk(isIncome)
                },
                containerColor = color,
                contentColor = KakeboTheme.colorSchema.onBackground
            ) {
                Icon(Icons.AutoMirrored.Filled.Send, stringResource(R.string.send))
            }
        }
    }
}

@MultiPreview
@Composable
fun OutcomeScreenPreview() {
    KakeboTheme {
        AddLinesScreen(
            state = AddLinesState.INITIAL,
            isIncome = false,
            userInteractions = AddLinesUserInteractions.Stub()
        ) {}
    }
}
