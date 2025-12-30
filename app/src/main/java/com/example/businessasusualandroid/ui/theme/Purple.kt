package com.example.businessasusualandroid.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val PurpleRoyal = Color(0xFF6A1B9A)
val PurpleNeon = Color(0xFFBA68C8)
val PurpleDeep = Color(0xFF4A148C)

val PurpleLightColors = lightColorScheme(
    primary = PurpleRoyal,
    onPrimary = Color.White,
    secondary = PurpleNeon,
    onSecondary = Color.Black,
    background = Color(0xFFF3E5F5),
    onBackground = PurpleDeep,
    surface = Color.White,
    onSurface = PurpleDeep
)

val PurpleDarkColors = darkColorScheme(
    primary = PurpleNeon,
    onPrimary = PurpleDeep,
    secondary = PurpleNeon,
    onSecondary = PurpleDeep,
    background = PurpleDeep,
    onBackground = Color.White,
    surface = Color(0xFF2E0A47),
    onSurface = Color.White
)