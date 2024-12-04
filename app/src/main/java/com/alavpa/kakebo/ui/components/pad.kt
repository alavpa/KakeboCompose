package com.alavpa.kakebo.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alavpa.kakebo.ui.theme.KakeboTheme

@Composable
fun Pad(userInteractions: PadUserInteractions, modifier: Modifier = Modifier) {
    Row(modifier) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = { userInteractions.onClickNumber("1") }) {
                Text("1")
            }
            Button(onClick = { userInteractions.onClickNumber("4") }) {
                Text("4")
            }
            Button(onClick = { userInteractions.onClickNumber("7") }) {
                Text("7")
            }
            Button(onClick = { userInteractions.onClickDelete() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "back"
                )
            }
        }
        Spacer(Modifier.width(KakeboTheme.space.vertical))
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = { userInteractions.onClickNumber("2") }) {
                Text("2")
            }
            Button(onClick = { userInteractions.onClickNumber("5") }) {
                Text("5")
            }
            Button(onClick = { userInteractions.onClickNumber("8") }) {
                Text("8")
            }
            Button(onClick = { userInteractions.onClickNumber("0") }) {
                Text("0")
            }
        }
        Spacer(Modifier.width(20.dp))
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = { userInteractions.onClickNumber("3") }) {
                Text("3")
            }
            Button(onClick = { userInteractions.onClickNumber("6") }) {
                Text("6")
            }
            Button(onClick = { userInteractions.onClickNumber("9") }) {
                Text("9")
            }
            Button(onClick = { userInteractions.onClickOk() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = "send"
                )
            }
        }
    }
}

interface PadUserInteractions {
    fun onClickNumber(number: String)
    fun onClickDelete()
    fun onClickOk()
}