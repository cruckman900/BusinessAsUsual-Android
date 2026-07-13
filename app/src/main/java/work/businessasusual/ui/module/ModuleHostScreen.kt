package work.businessasusual.ui.module

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import work.businessasusual.ui.components.BAUScreenScaffold
import work.businessasusual.ui.mobileui.MobileUiScreen

/**
 * Generic host for any backend-discovered module. Renders the module's
 * contract-driven UI (MobileUiScreen) inside the standard app scaffold,
 * so no per-module screen needs to be hard-coded.
 */
@Composable
fun ModuleHostScreen(
    navController: NavHostController,
    moduleId: String,
    onNavigate: (String) -> Unit,
    themeName: String,
    darkTheme: Boolean,
    onThemeChange: (String) -> Unit,
    onDarkThemeChange: (Boolean) -> Unit,
) {
    val title = moduleId.replaceFirstChar { it.uppercase() }
    BAUScreenScaffold(
        navController = navController,
        title = title,
        onNavigate = onNavigate,
        themeName = themeName,
        darkTheme = darkTheme,
        onThemeChange = onThemeChange,
        onDarkThemeChange = onDarkThemeChange,
        breadcrumbs = listOf("Dashboard", title)
    ) {
        MobileUiScreen(moduleId = moduleId)
    }
}
