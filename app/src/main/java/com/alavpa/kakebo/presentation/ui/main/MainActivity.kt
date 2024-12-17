package com.alavpa.kakebo.presentation.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.alavpa.kakebo.presentation.theme.KakeboTheme
import com.alavpa.kakebo.presentation.ui.main.compose.MainScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KakeboTheme { MainScreen() }
        }
    }
}
