package com.alavpa.kakebo.presentation.components

import MultiPreview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alavpa.kakebo.presentation.theme.KakeboTheme

@Composable
fun KTextField(
    modifier: Modifier = Modifier,
    label: String? = null,
    suffix: String? = null,
    prefix: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    isIncome: Boolean,
    value: String,
    onValueChange: (String) -> Unit
) {
    val color = if (isIncome) {
        KakeboTheme.colorSchema.incomeColor
    } else {
        KakeboTheme.colorSchema.outcomeColor
    }
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        colors = OutlinedTextFieldDefaults.colors().copy(
            focusedIndicatorColor = color,
            focusedLabelColor = color,
            cursorColor = color,
            textSelectionColors = TextSelectionColors(
                color,
                OutlinedTextFieldDefaults.colors().textSelectionColors.backgroundColor
            )
        ),
        prefix = prefix?.let { { Text(text = it) } },
        suffix = suffix?.let { { Text(text = it) } },
        label = label?.let { { Text(text = it) } },
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions
    )
}

@Composable
@MultiPreview
private fun KTextFieldPreview() {
    KakeboTheme {
        Column {
            KTextField(
                suffix = "¢",
                isIncome = true,
                value = "Hola Mundo!"
            ) { }
            VerticalSpacer(KakeboTheme.space.m)
            KTextField(
                isIncome = false,
                value = "Hola Mundo!"
            ) { }
        }
    }
}
