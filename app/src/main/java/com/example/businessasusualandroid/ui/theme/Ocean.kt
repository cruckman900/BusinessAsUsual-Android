package com.example.businessasusualandroid.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val OceanBlue = Color(0xFF0277BD)
val OceanTeal = Color(0xFF26C6DA)
val OceanDeep = Color(0xFF01579B)

val OceanLightColors = lightColorScheme(
    primary = OceanBlue,
    onPrimary = Color.White,
    secondary = OceanTeal,
    onSecondary = Color.Black,
    background = Color(0xFFE3F2FD),
    onBackground = OceanDeep,
    surface = Color.White,
    onSurface = OceanDeep
)

val OceanDarkColors = darkColorScheme(
    primary = OceanTeal,
    onPrimary = OceanDeep,
    secondary = OceanTeal,
    onSecondary = OceanDeep,
    background = OceanDeep,
    onBackground = Color.White,
    surface = Color(0xFF003C6C),
    onSurface = Color.White
)