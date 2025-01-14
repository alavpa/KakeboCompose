package com.alavpa.kakebo.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun InitializeOnce(block: () -> Unit) {
    LaunchedEffect(Unit) {
        block()
    }
}
