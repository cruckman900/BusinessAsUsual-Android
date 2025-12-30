package com.example.businessasusualandroid.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val ArmadaPurple = Color(0xFF4A148C)
val ArmadaTeal = Color(0xFF00E5C1)
val ArmadaSurface = Color(0xFF1A0F2A)

val ArmadaLightColors = lightColorScheme(
    primary = ArmadaPurple,
    onPrimary = Color.White,
    secondary = ArmadaTeal,
    onSecondary = Color.Black,
    surface = Color.White,
    onSurface = ArmadaPurple,
    background = Color(0xFFF8F5FC),
    onBackground = ArmadaPurple
)

val ArmadaDarkColors = darkColorScheme(
    primary = ArmadaTeal,
    onPrimary = ArmadaSurface,
    secondary = ArmadaTeal,
    onSecondary = ArmadaSurface,
    surface = ArmadaSurface,
    onSurface = Color.White,
    background = ArmadaPurple,
    onBackground = Color.White
)