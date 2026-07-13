package work.businessasusual.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import work.businessasusual.ui.dashboard.DashboardScreen
import work.businessasusual.ui.module.ModuleHostScreen
import work.businessasusual.ui.splash.SplashScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    themeName: String,
    darkTheme: Boolean,
    onThemeChange: (String) -> Unit,
    onDarkThemeChange: (Boolean) -> Unit
) {
    NavHost(navController, startDestination = "splash") {

        composable("splash") {
            SplashScreen {
                navController.navigate("dashboard") {
                    popUpTo("splash") { inclusive = true }
                }
            }
        }

        composable("dashboard") {
            DashboardScreen(
                navController = navController,
                onNavigate = { route -> navController.navigate(route) },
                onModuleClick = { module ->
                    navController.navigate(module.route)
                },
                themeName = themeName,
                darkTheme = darkTheme,
                onThemeChange = onThemeChange,
                onDarkThemeChange = onDarkThemeChange
            )
        }

        // Any backend-discovered module is hosted generically via its
        // contract-driven UI. No per-module route needs to be hard-coded.
        composable("module/{moduleId}") { backStackEntry ->
            val moduleId = backStackEntry.arguments?.getString("moduleId").orEmpty()
            ModuleHostScreen(
                navController = navController,
                moduleId = moduleId,
                onNavigate = { route -> navController.navigate(route) },
                themeName = themeName,
                darkTheme = darkTheme,
                onThemeChange = onThemeChange,
                onDarkThemeChange = onDarkThemeChange
            )
        }
    }
}
