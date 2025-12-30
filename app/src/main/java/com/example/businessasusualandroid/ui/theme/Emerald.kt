package com.example.businessasusualandroid.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val EmeraldGreen = Color(0xFF2E7D32)
val EmeraldGold = Color(0xFFFFD54F)
val EmeraldDeep = Color(0xFF1B5E20)

val EmeraldLightColors = lightColorScheme(
    primary = EmeraldGreen,
    onPrimary = Color.White,
    secondary = EmeraldGold,
    onSecondary = Color.Black,
    background = Color(0xFFE8F5E9),
    onBackground = EmeraldDeep,
    surface = Color.White,
    onSurface = EmeraldDeep
)

val EmeraldDarkColors = darkColorScheme(
    primary = EmeraldGold,
    onPrimary = EmeraldDeep,
    secondary = EmeraldGold,
    onSecondary = EmeraldDeep,
    background = EmeraldDeep,
    onBackground = Color.White,
    surface = Color(0xFF0D3B12),
    onSurface = Color.White
)