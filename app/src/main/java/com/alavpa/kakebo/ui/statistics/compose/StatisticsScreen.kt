package com.alavpa.kakebo.ui.statistics.compose

import MultiPreview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.alavpa.kakebo.ui.components.HorizontalSpacer
import com.alavpa.kakebo.ui.components.VerticalSpacer
import com.alavpa.kakebo.ui.theme.KakeboTheme

@Composable
fun StatisticsScreen() {
    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(KakeboTheme.space.horizontal)
    ) {
        item {
            Column {
                Text("Budget", style = KakeboTheme.typography.regularText)
                VerticalSpacer(KakeboTheme.space.s)
                Text("23444 - 6786767 = 767687678", style = KakeboTheme.typography.regularText)
                VerticalSpacer(KakeboTheme.space.l)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        "Budget with savings:",
                        style = KakeboTheme.typography.regularText,
                        modifier = Modifier.weight(1f)
                    )
                    HorizontalSpacer(KakeboTheme.space.s)
                    OutlinedTextField(
                        value = "444",
                        onValueChange = {},
                        modifier = Modifier.weight(1f)
                    )
                    HorizontalSpacer(KakeboTheme.space.s)
                    Text("€", style = KakeboTheme.typography.regularText)
                }
                VerticalSpacer(KakeboTheme.space.l)
                Text("23444 - 6786767 = 767687678", style = KakeboTheme.typography.regularText)
            }
        }
    }

}

@MultiPreview
@Composable
fun StatisticsPreview() {
    KakeboTheme {
        StatisticsScreen()
    }
}