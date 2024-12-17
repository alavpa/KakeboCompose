package com.alavpa.kakebo.presentation.components

import MultiPreview
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.alavpa.kakebo.presentation.theme.KakeboTheme

@Composable
private fun Pill(
    text: String,
    textColor: Color,
    backgroundColor: Color,
    textColorSelected: Color,
    backgroundColorSelected: Color,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    Text(
        text = text,
        color = if (isSelected) textColorSelected else textColor,
        modifier =
        Modifier
            .background(
                color = if (isSelected) backgroundColorSelected else backgroundColor,
                shape = RoundedCornerShape(KakeboTheme.cornerRadius.s)
            )
            .padding(KakeboTheme.space.s)
            .clickable(onClick = onClick)
    )
}

@Composable
private fun CategoryOutcomePill(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Pill(
        text = text,
        textColor = KakeboTheme.colorSchema.outcomeColor,
        backgroundColor = KakeboTheme.colorSchema.background,
        textColorSelected = KakeboTheme.colorSchema.onBackground,
        backgroundColorSelected = KakeboTheme.colorSchema.outcomeColor,
        isSelected = isSelected,
        onClick = onClick
    )
}

@Composable
private fun CategoryIncomePill(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Pill(
        text = text,
        textColor = KakeboTheme.colorSchema.incomeColor,
        backgroundColor = KakeboTheme.colorSchema.background,
        textColorSelected = KakeboTheme.colorSchema.onBackground,
        backgroundColorSelected = KakeboTheme.colorSchema.incomeColor,
        isSelected = isSelected,
        onClick = onClick
    )
}

@Composable
fun CategoryPill(
    text: String,
    isIncome: Boolean,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    if (isIncome) {
        CategoryIncomePill(text, isSelected, onClick)
    } else {
        CategoryOutcomePill(text, isSelected, onClick)
    }
}

@MultiPreview
@Composable
private fun IncomePillSelectedPreview() {
    KakeboTheme {
        Column {
            CategoryPill(
                text = "Extras",
                isIncome = true,
                isSelected = true,
                onClick = {}
            )
            VerticalSpacer(KakeboTheme.space.l)
            CategoryPill(
                text = "Extras",
                isIncome = true,
                isSelected = false,
                onClick = {}
            )
        }
    }
}

@MultiPreview
@Composable
private fun OutcomePillSelectedPreview() {
    KakeboTheme {
        Column {
            CategoryPill(
                text = "Extras",
                isIncome = false,
                isSelected = true,
                onClick = {}
            )
            VerticalSpacer(KakeboTheme.space.l)
            CategoryPill(
                text = "Extras",
                isIncome = false,
                isSelected = false,
                onClick = {}
            )
        }
    }
}
