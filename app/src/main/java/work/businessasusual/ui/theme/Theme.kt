package work.businessasusual.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowInsetsControllerCompat

@Composable
fun BAUTheme(
    themeName: String = "bau",
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colors = resolveTheme(themeName, darkTheme)

    // Sync status bar + nav bar with theme (native replacement for Accompanist).
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            val lightIcons = colors.primary.luminance() > 0.5f

            @Suppress("DEPRECATION")
            window.statusBarColor = colors.primary.toArgb()
            @Suppress("DEPRECATION")
            window.navigationBarColor = colors.primary.toArgb()

            WindowInsetsControllerCompat(window, view).apply {
                isAppearanceLightStatusBars = lightIcons
                isAppearanceLightNavigationBars = lightIcons
            }
        }
    }

    MaterialTheme(
        colorScheme = colors,
        typography = BAUTypography,
        shapes = BAUShapes,
        content = content
    )
}