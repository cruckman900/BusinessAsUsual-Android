package com.example.businessasusualandroid.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// BAU Brand Colors
val BAUPrimary = Color(0xFF1E88E5)      // Blue 600
val BAUPrimaryDark = Color(0xFF1565C0)  // Blue 800
val BAUAccent = Color(0xFFFFC107)       // Amber 500

// Light Theme
val BAULightColors = lightColorScheme(
    primary = BAUPrimary,
    onPrimary = Color.White,
    primaryContainer = BAUPrimaryDark,
    onPrimaryContainer = Color.White,

    secondary = BAUAccent,
    onSecondary = Color.Black,

    background = Color(0xFFF7F9FC),
    onBackground = Color(0xFF1A1A1A),

    surface = Color.White,
    onSurface = Color(0xFF1A1A1A)
)

// Dark Theme
val BAUDarkColors = darkColorScheme(
    primary = BAUPrimary,
    onPrimary = Color.White,
    primaryContainer = BAUPrimaryDark,
    onPrimaryContainer = Color.White,

    secondary = BAUAccent,
    onSecondary = Color.Black,

    background = Color(0xFF0F1116),
    onBackground = Color(0xFFE6E6E6),

    surface = Color(0xFF1A1C20),
    onSurface = Color(0xFFE6E6E6)
)
