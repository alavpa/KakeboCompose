package com.alavpa.kakebo.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alavpa.kakebo.R
import com.alavpa.kakebo.presentation.theme.KakeboTheme

@Composable
fun Pad(
    userInteractions: PadUserInteractions,
    isIncome: Boolean,
    modifier: Modifier = Modifier
) {
    val buttonColor =
        if (isIncome) {
            KakeboTheme.colorSchema.incomeColor
        } else {
            KakeboTheme.colorSchema.outcomeColor
        }
    Row(modifier) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            PadKey("1", color = buttonColor, onClick = { userInteractions.onClickNumber("1") })
            PadKey("4", color = buttonColor, onClick = { userInteractions.onClickNumber("4") })
            PadKey("7", color = buttonColor, onClick = { userInteractions.onClickNumber("7") })
            PadIcon(
                painterResource(R.drawable.backspace_24px),
                color = buttonColor,
                contentDescription = stringResource(R.string.delete)
            ) {
                userInteractions.onClickDelete()
            }
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            PadKey("2", color = buttonColor, onClick = { userInteractions.onClickNumber("2") })
            PadKey("5", color = buttonColor, onClick = { userInteractions.onClickNumber("5") })
            PadKey("8", color = buttonColor, onClick = { userInteractions.onClickNumber("8") })
            PadKey("0", color = buttonColor, onClick = { userInteractions.onClickNumber("0") })
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            PadKey("3", color = buttonColor, onClick = { userInteractions.onClickNumber("3") })
            PadKey("6", color = buttonColor, onClick = { userInteractions.onClickNumber("6") })
            PadKey("9", color = buttonColor, onClick = { userInteractions.onClickNumber("9") })
            PadKey("00", color = buttonColor, onClick = { userInteractions.onClickNumber("00") })
        }
    }
}

@Composable
private fun PadKey(
    text: String,
    color: Color,
    onClick: () -> Unit
) {
    Button(
        modifier =
        Modifier
            .padding(KakeboTheme.space.s)
            .size(width = 80.dp, height = 80.dp),
        colors = ButtonDefaults.buttonColors().copy(containerColor = color),
        onClick = onClick
    ) {
        Text(text, style = KakeboTheme.typography.padButtons)
    }
}

@Composable
private fun PadIcon(
    painter: Painter,
    color: Color,
    contentDescription: String,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .padding(KakeboTheme.space.s)
            .size(width = 80.dp, height = 80.dp),
        colors = ButtonDefaults.buttonColors().copy(containerColor = color),
        onClick = onClick
    ) {
        Icon(painter = painter, contentDescription = contentDescription)
    }
}

interface PadUserInteractions {
    fun onClickNumber(number: String)

    fun onClickDelete()

    fun onClickOk(isIncome: Boolean)
}
