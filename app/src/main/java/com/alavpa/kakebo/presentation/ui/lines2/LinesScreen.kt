package com.alavpa.kakebo.presentation.ui.lines2

import MultiPreview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.alavpa.kakebo.R
import com.alavpa.kakebo.presentation.components.HorizontalSpacer
import com.alavpa.kakebo.presentation.components.Pill
import com.alavpa.kakebo.presentation.components.VerticalSpacer
import com.alavpa.kakebo.presentation.theme.KakeboTheme

@Composable
fun LinesScreen() {
    var amount by remember { mutableStateOf("0") }
    var description by remember { mutableStateOf("") }
    var repeat by remember { mutableStateOf(false) }
    var categories by remember { mutableStateOf(listOf(false, false, false)) }
    Column(
        Modifier
            .fillMaxSize()
            .padding(
                vertical = KakeboTheme.space.vertical,
                horizontal = KakeboTheme.space.horizontal
            )
    ) {
        Column(Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(stringResource(R.string.repeat_per_month), Modifier.weight(1f))
                Switch(checked = repeat, onCheckedChange = { repeat = it })
            }
            VerticalSpacer(KakeboTheme.space.m)
            Row {
                Pill(text = stringResource(R.string.culture), categories[0]) {
                    categories = listOf(!categories[0], false, false)
                }
                HorizontalSpacer(KakeboTheme.space.m)
                Pill(text = stringResource(R.string.survival), categories[1]) {
                    categories = listOf(false, !categories[1], false)
                }
                HorizontalSpacer(KakeboTheme.space.m)
                Pill(text = stringResource(R.string.extras), categories[2]) {
                    categories = listOf(false, false, !categories[2])
                }
            }
            VerticalSpacer(KakeboTheme.space.m)
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = amount,
                onValueChange = { amount = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                )
            )
            VerticalSpacer(KakeboTheme.space.m)
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = description,
                label = {
                    Text(stringResource(R.string.concept))
                },
                onValueChange = { description = it },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Send
                )
            )
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {}
        ) {
            Text(stringResource(R.string.send))
        }
    }
}

@Composable
@MultiPreview
private fun LinesScreenPreview() {
    KakeboTheme {
        LinesScreen()
    }
}
