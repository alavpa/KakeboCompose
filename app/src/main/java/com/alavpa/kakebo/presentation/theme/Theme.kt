package com.alavpa.kakebo.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import com.alavpa.kakebo.presentation.theme.KakeboPalette.Pink40
import com.alavpa.kakebo.presentation.theme.KakeboPalette.Pink80
import com.alavpa.kakebo.presentation.theme.KakeboPalette.Purple40
import com.alavpa.kakebo.presentation.theme.KakeboPalette.Purple80
import com.alavpa.kakebo.presentation.theme.KakeboPalette.PurpleGrey40
import com.alavpa.kakebo.presentation.theme.KakeboPalette.PurpleGrey80

private val DarkColorScheme =
    darkColorScheme(
        primary = Purple80,
        secondary = PurpleGrey80,
        tertiary = Pink80,
 //       background = PurpleGrey40
    )

private val LightColorScheme =
    lightColorScheme(
        primary = Purple40,
        secondary = PurpleGrey40,
        tertiary = Pink40,
  //      background = Pink80
    )

@Composable
fun KakeboTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = getColorSchema(darkTheme),
        typography = Typography,
        content = { Surface { content() } }
    )
}

@Composable
private fun getColorSchema(darkTheme: Boolean = isSystemInDarkTheme()): ColorScheme {
    return when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
}

object KakeboTheme {
    val colorSchema: KakeboColorSchema
        @Composable
        get() = kakeboColorSchema(isSystemInDarkTheme())

    val materialColorScheme: ColorScheme
        @Composable
        get() = getColorSchema(isSystemInDarkTheme())

    val space = Space

    val cornerRadius = CornerRadius

    val typography = kakeboTypography

    val borderSize = BorderSize
}
