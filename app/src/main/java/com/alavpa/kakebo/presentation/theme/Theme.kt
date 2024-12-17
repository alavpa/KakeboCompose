package com.alavpa.kakebo.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
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
        background = Pink40
    )

private val LightColorScheme =
    lightColorScheme(
        primary = Purple40,
        secondary = PurpleGrey40,
        tertiary = Pink40,
        background = Pink80
    )

@Composable
fun KakeboTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    // dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme =
        when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }

            darkTheme -> DarkColorScheme
            else -> LightColorScheme
        }
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

object KakeboTheme {
    val colorSchema: KakeboColorSchema
        @Composable
        get() = kakeboColorSchema(isSystemInDarkTheme())

    val space = Space

    val cornerRadius = CornerRadius

    val typography = kakeboTypography

    val borderSize = BorderSize
}
