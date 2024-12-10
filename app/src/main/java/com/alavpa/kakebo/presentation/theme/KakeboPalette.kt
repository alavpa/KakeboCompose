package com.alavpa.kakebo.presentation.theme

import androidx.compose.ui.graphics.Color
import com.alavpa.kakebo.presentation.theme.KakeboPalette.BrickRed
import com.alavpa.kakebo.presentation.theme.KakeboPalette.BurntOrange
import com.alavpa.kakebo.presentation.theme.KakeboPalette.Coral
import com.alavpa.kakebo.presentation.theme.KakeboPalette.DarkGold
import com.alavpa.kakebo.presentation.theme.KakeboPalette.DeepRed
import com.alavpa.kakebo.presentation.theme.KakeboPalette.GoldenSand
import com.alavpa.kakebo.presentation.theme.KakeboPalette.LightCoral
import com.alavpa.kakebo.presentation.theme.KakeboPalette.LightOrange
import com.alavpa.kakebo.presentation.theme.KakeboPalette.MustardYellow
import com.alavpa.kakebo.presentation.theme.KakeboPalette.Neutral10
import com.alavpa.kakebo.presentation.theme.KakeboPalette.Neutral6
import com.alavpa.kakebo.presentation.theme.KakeboPalette.Neutral90
import com.alavpa.kakebo.presentation.theme.KakeboPalette.Neutral98
import com.alavpa.kakebo.presentation.theme.KakeboPalette.PaleYellow
import com.alavpa.kakebo.presentation.theme.KakeboPalette.Peach
import com.alavpa.kakebo.presentation.theme.KakeboPalette.Pink40
import com.alavpa.kakebo.presentation.theme.KakeboPalette.Pink80
import com.alavpa.kakebo.presentation.theme.KakeboPalette.Purple40
import com.alavpa.kakebo.presentation.theme.KakeboPalette.Purple80
import com.alavpa.kakebo.presentation.theme.KakeboPalette.PurpleGrey40
import com.alavpa.kakebo.presentation.theme.KakeboPalette.PurpleGrey80
import com.alavpa.kakebo.presentation.theme.KakeboPalette.RoseRed
import com.alavpa.kakebo.presentation.theme.KakeboPalette.SoftRed
import com.alavpa.kakebo.presentation.theme.KakeboPalette.Terracotta

object KakeboPalette {
    //Dark
    val DeepRed = Color(0xFFCC3333)
    val BurntOrange = Color(0xFFD96D2C)
    val MustardYellow = Color(0xFFF2BE48)
    val Terracotta = Color(0xFFF2BE48)
    val Coral = Color(0xFFFF7F50)
    val DarkGold = Color(0xFFB7860B)
    val BrickRed = Color(0xFFB22222)
    val Purple40 = Color(0xFF6650a4)
    val PurpleGrey40 = Color(0xFF625b71)
    val Pink40 = Color(0xFF7D5260)
    val Neutral6: Color = Color(red = 20, green = 18, blue = 24)
    val Neutral90 = Color(red = 230, green = 224, blue = 233)

    //Light
    val SoftRed = Color(0xFFFF6666)
    val LightOrange = Color(0xFFFFA573)
    val PaleYellow = Color(0xFFF9D78B)
    val Peach = Color(0xFFE8A37A)
    val LightCoral = Color(0xFFFF9985)
    val GoldenSand = Color(0xFFE2B94E)
    val RoseRed = Color(0xFFCC5757)
    val Purple80 = Color(0xFFD0BCFF)
    val PurpleGrey80 = Color(0xFFCCC2DC)
    val Pink80 = Color(0xFFEFB8C8)
    val Neutral98 = Color(red = 254, green = 247, blue = 255)
    val Neutral10 = Color(red = 29, green = 27, blue = 32)
}

data class KakeboColorSchema(
    val color1: Color,
    val color2: Color,
    val color3: Color,
    val color4: Color,
    val color5: Color,
    val color6: Color,
    val color7: Color,
    val color8: Color,
    val color9: Color,
    val color10: Color,
    val background: Color,
    val onBackground: Color
)

fun lightKakeboColorSchema() = KakeboColorSchema(
    SoftRed,
    LightOrange,
    PaleYellow,
    Peach,
    LightCoral,
    GoldenSand,
    RoseRed,
    Purple80,
    PurpleGrey80,
    Pink80,
    Neutral6,
    Neutral90
)

fun darkKakeboColorSchema() = KakeboColorSchema(
    DeepRed,
    BurntOrange,
    MustardYellow,
    Terracotta,
    Coral,
    DarkGold,
    BrickRed,
    Purple40,
    PurpleGrey40,
    Pink40,
    Neutral98,
    Neutral10
)


fun kakeboColorSchema(isDarkMode: Boolean): KakeboColorSchema {
    return if (isDarkMode) darkKakeboColorSchema() else lightKakeboColorSchema()
}

