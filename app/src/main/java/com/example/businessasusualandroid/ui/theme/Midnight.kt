package com.example.businessasusualandroid.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val MidnightBlue = Color(0xFF0A1A2F)
val MidnightCyan = Color(0xFF00E5FF)
val MidnightSurface = Color(0xFF12263A)

val MidnightLightColors = lightColorScheme(
    primary = MidnightBlue,
    onPrimary = Color.White,
    secondary = MidnightCyan,
    onSecondary = Color.Black,
    surface = Color.White,
    onSurface = MidnightBlue,
    background = Color(0xFFF5F8FA),
    onBackground = MidnightBlue
)

val MidnightDarkColors = darkColorScheme(
    primary = MidnightCyan,
    onPrimary = MidnightBlue,
    secondary = MidnightCyan,
    onSecondary = MidnightBlue,
    surface = MidnightSurface,
    onSurface = Color.White,
    background = MidnightBlue,
    onBackground = Color.White
)