package com.alavpa.kakebo.presentation.ui.statistics.compose

import MultiPreview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.alavpa.kakebo.R
import com.alavpa.kakebo.presentation.components.BudgetBox
import com.alavpa.kakebo.presentation.components.DynamicButton
import com.alavpa.kakebo.presentation.components.HorizontalSpacer
import com.alavpa.kakebo.presentation.components.InitializeOnce
import com.alavpa.kakebo.presentation.components.LineItem
import com.alavpa.kakebo.presentation.components.VerticalSpacer
import com.alavpa.kakebo.presentation.models.CategoryUI
import com.alavpa.kakebo.presentation.models.LineUI
import com.alavpa.kakebo.presentation.theme.KakeboTheme
import com.alavpa.kakebo.presentation.ui.statistics.StatisticsState
import com.alavpa.kakebo.presentation.ui.statistics.StatisticsUserInteractions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticsScreen(
    state: StatisticsState,
    userInteractions: StatisticsUserInteractions
) {
    InitializeOnce {
        userInteractions.onInitializeOnce()
    }
    if (state.showDialogParams != null) {
        BasicAlertDialog(onDismissRequest = { userInteractions.onCancelDelete() }) {
            Surface {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = KakeboTheme.space.horizontal,
                            vertical = KakeboTheme.space.vertical
                        )
                ) {
                    Text(
                        stringResource(R.string.are_you_sure),
                        style = KakeboTheme.typography.regularText
                    )
                    VerticalSpacer(KakeboTheme.space.l)
                    Row(Modifier.align(Alignment.End)) {
                        DynamicButton(
                            text = stringResource(R.string.cancel),
                            onClick = userInteractions::onCancelDelete,
                            isIncome = state.showDialogParams.isIncome
                        )
                        HorizontalSpacer(KakeboTheme.space.m)
                        DynamicButton(
                            text = stringResource(R.string.delete),
                            onClick = userInteractions::onConfirmDelete,
                            isIncome = state.showDialogParams.isIncome
                        )
                    }
                }
            }
        }
    }

    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(KakeboTheme.space.horizontal)
    ) {
        item {
            Header(state, userInteractions::onSavingsChanged)
        }
        items(items = state.lines, key = { line -> line.id }) { line ->
            LineItem(line) {
                userInteractions.onClickDeleteLine(line.id, line.isIncome)
            }
        }
    }
}

@Composable
private fun Header(state: StatisticsState, onSavingsChanged: (String) -> Unit) {
    Column {
        BudgetBox(
            title = stringResource(R.string.budget),
            subtitle = stringResource(R.string.operation, state.income, state.outcome),
            result = state.budgetText,
            color = KakeboTheme.colorSchema.incomeColor
        )
        VerticalSpacer(KakeboTheme.space.l)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.weight(1.0f),
                text = stringResource(R.string.savings),
                style = KakeboTheme.typography.titleLarge
            )
            HorizontalSpacer(KakeboTheme.space.s)
            OutlinedTextField(
                modifier = Modifier.weight(0.5f),
                value = state.savings,
                onValueChange = onSavingsChanged,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                textStyle = KakeboTheme.typography.titleLarge
            )
        }
        VerticalSpacer(KakeboTheme.space.l)
        BudgetBox(
            title = stringResource(R.string.budget_with_savings),
            subtitle = stringResource(R.string.operation, state.budgetText, state.savingsText),
            result = state.budgetWithSavings,
            color = KakeboTheme.colorSchema.incomeColor,
            modifier = Modifier.align(Alignment.End)
        )
    }
}

@MultiPreview
@Composable
fun StatisticsPreview() {
    KakeboTheme {
        StatisticsScreen(
            state =
            StatisticsState.INITIAL
                .copy(
                    income = "$100.00",
                    outcome = "$50.00",
                    budgetText = "$50.00",
                    budget = 0,
                    savings = "30",
                    budgetWithSavings = "20",
                    lines = listOf(
                        LineUI(
                            id = 1,
                            amount = "12.00 €",
                            date = "ene 2025",
                            isIncome = true,
                            repeatPerMonth = true,
                            category = CategoryUI.Culture,
                            description = "This is description"
                        ),
                        LineUI(
                            id = 2,
                            amount = "12.00 €",
                            date = "ene 2025",
                            isIncome = false,
                            repeatPerMonth = true,
                            category = CategoryUI.Culture,
                            description = "This is description"
                        ),
                        LineUI(
                            id = 3,
                            amount = "12.00 €",
                            date = "ene 2025",
                            isIncome = false,
                            repeatPerMonth = false,
                            category = CategoryUI.Culture,
                            description = "This is description"
                        ),
                        LineUI(
                            id = 4,
                            amount = "12.00 €",
                            date = "ene 2025",
                            isIncome = true,
                            repeatPerMonth = true,
                            category = CategoryUI.Culture,
                            description = ""
                        )
                    )
                ),
            userInteractions = StatisticsUserInteractions.Stub()
        )
    }
}
