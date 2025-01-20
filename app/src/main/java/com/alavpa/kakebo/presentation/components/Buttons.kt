package com.alavpa.kakebo.presentation.components

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.alavpa.kakebo.presentation.theme.KakeboTheme

@Composable
private fun KakeboButton(
    modifier: Modifier = Modifier,
    text: String,
    containerColor: Color,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonColors(
            containerColor = containerColor,
            contentColor = KakeboTheme.colorSchema.onBackground,
            disabledContentColor = ButtonDefaults.buttonColors().disabledContentColor,
            disabledContainerColor = ButtonDefaults.buttonColors().disabledContainerColor
        )
    ) {
        Text(text)
    }
}

@Composable
fun IncomeButton(modifier: Modifier, text: String, onClick: () -> Unit) {
    KakeboButton(
        text = text,
        modifier = modifier,
        containerColor = KakeboTheme.colorSchema.incomeColor,
        onClick = onClick
    )
}

@Composable
fun OutcomeButton(modifier: Modifier, text: String, onClick: () -> Unit) {
    KakeboButton(
        text = text,
        modifier = modifier,
        containerColor = KakeboTheme.colorSchema.outcomeColor,
        onClick = onClick
    )
}

@Composable
fun DynamicButton(
    modifier: Modifier = Modifier,
    text: String,
    isIncome: Boolean,
    onClick: () -> Unit
) {
    if (isIncome) {
        IncomeButton(modifier = modifier, text = text, onClick = onClick)
    } else {
        OutcomeButton(modifier = modifier, text = text, onClick = onClick)
    }
}
