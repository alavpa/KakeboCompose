package com.alavpa.kakebo.presentation.components

import MultiPreview
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.alavpa.kakebo.R
import com.alavpa.kakebo.presentation.models.CategoryUI
import com.alavpa.kakebo.presentation.models.LineUI
import com.alavpa.kakebo.presentation.theme.KakeboTheme

@Composable
fun LineItem(line: LineUI, onClickDelete: () -> Unit) {
    val colorText = if (line.isIncome) {
        KakeboTheme.colorSchema.incomeColor
    } else {
        KakeboTheme.colorSchema.outcomeColor
    }
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    Box(
        Modifier
            .fillMaxWidth()
            .padding(top = KakeboTheme.space.m)
            .border(
                color = colorText,
                width = KakeboTheme.borderSize.m,
                shape = RoundedCornerShape(KakeboTheme.cornerRadius.m)
            )
            .clickable { isExpanded = !isExpanded }
    ) {
        Column(Modifier.padding(KakeboTheme.space.s)) {
            Row {
                Text(
                    line.amount,
                    style = KakeboTheme.typography.regularText,
                    color = colorText
                )
                if (line.repeatPerMonth) {
                    HorizontalSpacer(KakeboTheme.space.s)
                    Text(
                        stringResource(R.string.per_month),
                        style = KakeboTheme.typography.regularText,
                        color = colorText
                    )
                }
                Spacer(Modifier.weight(1f, fill = true))
                Text(
                    line.date,
                    style = KakeboTheme.typography.regularText,
                    color = colorText
                )
            }
            VerticalSpacer(KakeboTheme.space.s)
            Text(
                stringResource(line.category.resId),
                style = KakeboTheme.typography.regularText,
                color = colorText
            )
            if (isExpanded) {
                if (line.description.isNotEmpty()) {
                    VerticalSpacer(KakeboTheme.space.s)
                    Text(
                        line.description,
                        style = KakeboTheme.typography.regularText,
                        color = colorText
                    )
                }
                VerticalSpacer(KakeboTheme.space.s)
                if (line.isIncome) {
                    IncomeButton(
                        text = stringResource(R.string.delete),
                        modifier = Modifier.align(Alignment.End),
                        onClick = onClickDelete
                    )
                } else {
                    OutcomeButton(
                        text = stringResource(R.string.delete),
                        modifier = Modifier.align(Alignment.End),
                        onClick = onClickDelete
                    )
                }
            }
        }
    }
}

@MultiPreview
@Composable
private fun KakeboIncomeItemPreview() {
    KakeboTheme {
        LineItem(
            LineUI(
                id = 1,
                amount = "13,00 €",
                date = "ene 2025",
                isIncome = true,
                repeatPerMonth = true,
                category = CategoryUI.Culture,
                description = "This is description"
            )
        ) {}
    }
}

@MultiPreview
@Composable
private fun KakeboOutcomeItemPreview() {
    KakeboTheme {
        LineItem(
            LineUI(
                id = 1,
                amount = "13.00€",
                date = "ene 2025",
                isIncome = false,
                repeatPerMonth = false,
                category = CategoryUI.Culture,
                description = ""
            )
        ) {}
    }
}
