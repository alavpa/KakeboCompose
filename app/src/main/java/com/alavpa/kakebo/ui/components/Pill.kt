package com.alavpa.kakebo.ui.components

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
import com.alavpa.kakebo.ui.theme.KakeboPalette
import com.alavpa.kakebo.ui.theme.KakeboTheme

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
        modifier = Modifier
            .background(
                color = if (isSelected) backgroundColorSelected else backgroundColor,
                shape = RoundedCornerShape(KakeboTheme.cornerRadius.s)
            )
            .padding(KakeboTheme.space.s)
            .clickable(onClick = onClick)
    )
}

@Composable
fun CategoryPill(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Pill(
        text = text,
        textColor = KakeboPalette.Neutral6,
        backgroundColor = KakeboPalette.Neutral90,
        textColorSelected = KakeboPalette.Neutral90,
        backgroundColorSelected = KakeboPalette.Neutral6,
        isSelected = isSelected,
        onClick = onClick
    )
}

@MultiPreview
@Composable
fun PillSelectedPreview() {
    KakeboTheme {
        Column {
            CategoryPill(
                text = "Extras",
                isSelected = true,
                onClick = {}
            )
            VerticalSpacer(KakeboTheme.space.l)
            CategoryPill(
                text = "Extras",
                isSelected = false,
                onClick = {}
            )
        }
    }
}