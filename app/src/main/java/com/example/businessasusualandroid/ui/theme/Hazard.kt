package com.example.businessasusualandroid.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// Hazard brand colors
val HazardYellow = Color(0xFFFFD600)   // Bright industrial yellow
val HazardBlack = Color(0xFF0A0A0A)    // Deep black
val HazardGray = Color(0xFF1E1E1E)     // Graphite surface

// Light Hazard
val HazardLightColors = lightColorScheme(
    primary = HazardYellow,
    onPrimary = HazardBlack,
    primaryContainer = HazardBlack,
    onPrimaryContainer = HazardYellow,

    secondary = HazardYellow,
    onSecondary = HazardBlack,

    background = Color(0xFFFDFDFD),
    onBackground = HazardBlack,

    surface = Color.White,
    onSurface = HazardBlack
)

// Dark Hazard
val HazardDarkColors = darkColorScheme(
    primary = HazardYellow,
    onPrimary = HazardBlack,
    primaryContainer = HazardGray,
    onPrimaryContainer = HazardYellow,

    secondary = HazardYellow,
    onSecondary = HazardBlack,

    background = HazardBlack,
    onBackground = Color.White,

    surface = HazardGray,
    onSurface = Color.White
)