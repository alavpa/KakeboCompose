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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.alavpa.kakebo.R
import com.alavpa.kakebo.presentation.components.HorizontalSpacer
import com.alavpa.kakebo.presentation.components.InitializeOnce
import com.alavpa.kakebo.presentation.components.VerticalSpacer
import com.alavpa.kakebo.presentation.ui.statistics.StatisticsState
import com.alavpa.kakebo.presentation.ui.statistics.StatisticsUserInteractions
import com.alavpa.kakebo.presentation.theme.KakeboTheme

@Composable
fun StatisticsScreen(state: StatisticsState, userInteractions: StatisticsUserInteractions) {
    InitializeOnce {
        userInteractions.onInitializeOnce()
    }
    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(KakeboTheme.space.horizontal)
    ) {
        item {
            Column {
                Text(stringResource(R.string.budget), style = KakeboTheme.typography.titleLarge)
                VerticalSpacer(KakeboTheme.space.s)
                Text(
                    text = stringResource(
                        R.string.operation,
                        state.income,
                        state.outcome,
                        state.budgetText
                    ),
                    style = KakeboTheme.typography.titleLarge
                )
                VerticalSpacer(KakeboTheme.space.l)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier.weight(1.0f),
                        text = stringResource(R.string.budget_with_savings),
                        style = KakeboTheme.typography.titleLarge,
                    )
                    HorizontalSpacer(KakeboTheme.space.s)
                    OutlinedTextField(
                        modifier = Modifier.weight(0.5f),
                        value = state.savings,
                        onValueChange = userInteractions::onSavingsChanged,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        textStyle = KakeboTheme.typography.titleLarge
                    )
                }
                VerticalSpacer(KakeboTheme.space.l)
                Text(
                    text = stringResource(
                        R.string.operation,
                        state.budgetText,
                        state.savingsText,
                        state.budgetWithSavings
                    ),
                    style = KakeboTheme.typography.titleLarge
                )
            }
        }
        items(state.lines) { line ->
            val colorText = if (line.isIncome) {
                KakeboTheme.colorSchema.color8
            } else {
                KakeboTheme.colorSchema.color1
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = KakeboTheme.space.vertical)
            ) {
                Text(
                    line.amount,
                    Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    style = KakeboTheme.typography.regularText,
                    color = colorText
                )
                Text(
                    line.date,
                    style = KakeboTheme.typography.regularText,
                    color = colorText
                )
            }
        }
    }
}

@MultiPreview
@Composable
fun StatisticsPreview() {
    KakeboTheme {
        StatisticsScreen(
            state = StatisticsState.INITIAL.copy(
                income = "100",
                outcome = "50",
                budgetText = "50",
                budget = 0,
                savings = "30",
                budgetWithSavings = "20"
            ),
            userInteractions = StatisticsUserInteractions.Stub()
        )
    }
}