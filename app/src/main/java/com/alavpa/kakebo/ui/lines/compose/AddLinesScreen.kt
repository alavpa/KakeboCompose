package com.alavpa.kakebo.ui.lines.compose

import MultiPreview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.alavpa.kakebo.R
import com.alavpa.kakebo.ui.components.CategoryPill
import com.alavpa.kakebo.ui.components.InitializeOnce
import com.alavpa.kakebo.ui.components.Pad
import com.alavpa.kakebo.ui.components.VerticalSpacer
import com.alavpa.kakebo.ui.lines.AddLinesState
import com.alavpa.kakebo.ui.lines.AddLinesUserInteractions
import com.alavpa.kakebo.ui.theme.KakeboTheme

@Composable
fun AddLinesScreen(
    state: AddLinesState,
    isIncome: Boolean,
    userInteractions: AddLinesUserInteractions,
    showSnackbarMessage: suspend (String) -> Unit
) {
    InitializeOnce { userInteractions.onInitializeOnce(isIncome) }
    val successMessage = if (isIncome) {
        stringResource(R.string.income_success_message)
    } else {
        stringResource(R.string.outcome_success_message)
    }
    LaunchedEffect(state.showSuccess) {
        if (state.showSuccess) {
            showSnackbarMessage(successMessage)
        }
    }
    Box(
        Modifier
            .fillMaxSize()
            .padding(KakeboTheme.space.horizontal)
    ) {

        LazyColumn {
            item {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = state.isFixedOutcome,
                            onCheckedChange = { isFixed ->
                                userInteractions.onIsFixedOutcomeChanged(
                                    isFixed
                                )
                            }
                        )
                        Text("Repeat each month.")
                    }

                    VerticalSpacer(height = KakeboTheme.space.vertical)
                    LazyRow(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        items(state.categories) { category ->
                            CategoryPill(
                                stringResource(category.first.resId),
                                category.second
                            ) {
                                userInteractions.onClickCategory(category)
                            }
                        }
                    }
                    VerticalSpacer(height = KakeboTheme.space.vertical)
                    Text(text = state.formattedText, fontSize = 56.sp)
                    Pad(userInteractions, isIncome)
                    VerticalSpacer(height = KakeboTheme.space.vertical)
                    TextField(
                        value = state.description,
                        onValueChange = { userInteractions.onDescriptionChanged(it) },
                        label = { Text(stringResource(R.string.concept)) },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3,
                        maxLines = 5
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
                        style = KakeboTheme.typography.titleLarge,
                        color = colorText
                    )
                    Text(
                        line.date,
                        style = KakeboTheme.typography.titleLarge,
                        color = colorText
                    )
                }
            }
        }
    }
}

@MultiPreview
@Composable
fun OutcomeScreenPreview() {
    KakeboTheme {
        AddLinesScreen(
            AddLinesState.INITIAL,
            isIncome = false,
            AddLinesUserInteractions.Stub(),
            {}
        )
    }
}