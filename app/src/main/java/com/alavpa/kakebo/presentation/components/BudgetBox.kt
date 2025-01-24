package com.alavpa.kakebo.presentation.components

import MultiPreview
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.alavpa.kakebo.R
import com.alavpa.kakebo.presentation.theme.KakeboTheme

@Composable
fun BudgetBox(
    title: String,
    subtitle: String,
    result: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier.then(
            Modifier
                .border(
                    width = KakeboTheme.borderSize.m,
                    color = color,
                    shape = RoundedCornerShape(KakeboTheme.cornerRadius.m)
                )
                .padding(KakeboTheme.space.s)
        )
    ) {
        Text(
            title,
            style = KakeboTheme.typography.regularText,
            color = color
        )
        Text(subtitle, style = KakeboTheme.typography.smallText, color = color)
        Text(result, style = KakeboTheme.typography.padText, color = color)
    }
}

@MultiPreview
@Composable
fun BudgetBoxPreview() {
    KakeboTheme {
        BudgetBox(
            title = stringResource(R.string.budget),
            subtitle = "$500.40 - $234.78 =",
            result = "$123.67",
            color = Color.Red
        )
    }
}
