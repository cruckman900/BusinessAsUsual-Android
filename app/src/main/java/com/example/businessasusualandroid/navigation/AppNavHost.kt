package com.example.businessasusualandroid.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.businessasusualandroid.ui.dashboard.DashboardScreen
import com.example.businessasusualandroid.ui.hr.HrScreen
import com.example.businessasusualandroid.ui.finance.FinanceScreen
import com.example.businessasusualandroid.ui.crm.CrmScreen
import com.example.businessasusualandroid.ui.splash.SplashScreen

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
                    navController.navigate("module/${module.id}")
                },
                themeName = themeName,
                darkTheme = darkTheme,
                onThemeChange = onThemeChange,
                onDarkThemeChange = onDarkThemeChange
            )
        }

        composable("module/{moduleId}") { backStackEntry ->
            when (backStackEntry.arguments?.getString("moduleId")) {

                "hr" -> HrScreen(
                    navController = navController,
                    onNavigate = { route -> navController.navigate(route) },
                    themeName = themeName,
                    darkTheme = darkTheme,
                    onThemeChange = onThemeChange,
                    onDarkThemeChange = onDarkThemeChange
                )

                "finance" -> FinanceScreen(
                    navController = navController,
                    onNavigate = { route -> navController.navigate(route) },
                    themeName = themeName,
                    darkTheme = darkTheme,
                    onThemeChange = onThemeChange,
                    onDarkThemeChange = onDarkThemeChange
                )

                "crm" -> CrmScreen(
                    navController = navController,
                    onNavigate = { route -> navController.navigate(route) },
                    themeName = themeName,
                    darkTheme = darkTheme,
                    onThemeChange = onThemeChange,
                    onDarkThemeChange = onDarkThemeChange
                )

                else -> DashboardScreen(
                    navController = navController,
                    onNavigate = { route -> navController.navigate(route) },
                    onModuleClick = { module ->
                        navController.navigate("module/${module.id}")
                    },
                    themeName = themeName,
                    darkTheme = darkTheme,
                    onThemeChange = onThemeChange,
                    onDarkThemeChange = onDarkThemeChange
                )
            }
        }
    }
}