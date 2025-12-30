package com.example.businessasusualandroid.ui.theme

import androidx.compose.material3.ColorScheme

fun resolveTheme(themeName: String, dark: Boolean): ColorScheme =
    when (themeName) {
        "bau" -> if (dark) BAUDarkColors else BAULightColors
        "hazard" -> if (dark) HazardDarkColors else HazardLightColors
        "midnight" -> if (dark) MidnightDarkColors else MidnightLightColors
        "armada" -> if (dark) ArmadaDarkColors else ArmadaLightColors
        "ocean" -> if (dark) OceanDarkColors else OceanLightColors
        "steel" -> if (dark) SteelDarkColors else SteelLightColors
        "sunset" -> if (dark) SunsetDarkColors else SunsetLightColors
        "emerald" -> if (dark) EmeraldDarkColors else EmeraldLightColors
        "purple" -> if (dark) PurpleDarkColors else PurpleLightColors
        "brownstone" -> if (dark) BrownstoneDarkColors else BrownstoneLightColors
        else -> if (dark) BAUDarkColors else BAULightColors
}