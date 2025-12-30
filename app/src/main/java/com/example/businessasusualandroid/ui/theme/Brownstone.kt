package com.example.businessasusualandroid.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val BrownstoneBrick = Color(0xFF6D4C41)
val BrownstoneSand = Color(0xFFD7CCC8)
val BrownstoneDeep = Color(0xFF4E342E)

val BrownstoneLightColors = lightColorScheme(
    primary = BrownstoneBrick,
    onPrimary = Color.White,
    secondary = BrownstoneSand,
    onSecondary = Color.Black,
    background = Color(0xFFEFEBE9),
    onBackground = BrownstoneDeep,
    surface = Color.White,
    onSurface = BrownstoneDeep
)

val BrownstoneDarkColors = darkColorScheme(
    primary = BrownstoneSand,
    onPrimary = BrownstoneDeep,
    secondary = BrownstoneSand,
    onSecondary = BrownstoneDeep,
    background = BrownstoneDeep,
    onBackground = Color.White,
    surface = Color(0xFF3E2723),
    onSurface = Color.White
)