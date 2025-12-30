package com.example.businessasusualandroid.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val SteelGray = Color(0xFF37474F)
val SteelBlue = Color(0xFF64B5F6)
val SteelSurface = Color(0xFF263238)

val SteelLightColors = lightColorScheme(
    primary = SteelGray,
    onPrimary = Color.White,
    secondary = SteelBlue,
    onSecondary = Color.Black,
    background = Color(0xFFF0F4F7),
    onBackground = SteelGray,
    surface = Color.White,
    onSurface = SteelGray
)

val SteelDarkColors = darkColorScheme(
    primary = SteelBlue,
    onPrimary = SteelSurface,
    secondary = SteelBlue,
    onSecondary = SteelSurface,
    background = SteelSurface,
    onBackground = Color.White,
    surface = Color(0xFF1C262B),
    onSurface = Color.White
)