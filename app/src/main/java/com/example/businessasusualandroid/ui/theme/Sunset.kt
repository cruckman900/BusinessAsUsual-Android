package com.example.businessasusualandroid.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val SunsetOrange = Color(0xFFFF7043)
val SunsetPink = Color(0xFFF06292)
val SunsetDeep = Color(0xFFBF360C)

val SunsetLightColors = lightColorScheme(
    primary = SunsetOrange,
    onPrimary = Color.White,
    secondary = SunsetPink,
    onSecondary = Color.Black,
    background = Color(0xFFFFF3E0),
    onBackground = SunsetDeep,
    surface = Color.White,
    onSurface = SunsetDeep
)

val SunsetDarkColors = darkColorScheme(
    primary = SunsetPink,
    onPrimary = SunsetDeep,
    secondary = SunsetPink,
    onSecondary = SunsetDeep,
    background = SunsetDeep,
    onBackground = Color.White,
    surface = Color(0xFF4E1F0A),
    onSurface = Color.White
)