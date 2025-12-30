package com.example.businessasusualandroid.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.luminance
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun BAUTheme(
    themeName: String = "bau",
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colors = resolveTheme(themeName, darkTheme)

    // Sync status bar + nav bar with theme
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(
            color = colors.primary,
            darkIcons = colors.primary.luminance() > 0.5f
        )

        systemUiController.setNavigationBarColor(
            color = colors.primary,
            darkIcons = colors.primary.luminance() > 0.5f
        )
    }

    MaterialTheme(
        colorScheme = colors,
        typography = BAUTypography,
        shapes = BAUShapes,
        content = content
    )
}