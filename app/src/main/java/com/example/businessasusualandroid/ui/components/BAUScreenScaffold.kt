package com.example.businessasusualandroid.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.coroutines.launch
import kotlin.collections.get

@Composable
fun BAUScreenScaffold(
    navController: NavHostController,
    title: String,
    onNavigate: (String) -> Unit,
    themeName: String,
    darkTheme: Boolean,
    onThemeChange: (String) -> Unit,
    onDarkThemeChange: (Boolean) -> Unit,
    breadcrumbs: List<String>,
    content: @Composable () -> Unit
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val themeDrawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val rawRoute = navBackStackEntry?.destination?.route
    val moduleId = navBackStackEntry?.arguments?.getString("moduleId")
    val currentRoute = moduleId?.let { "module/$it" } ?: rawRoute

    val onBreadcrumbNavigate: (Int) -> Unit = { index ->
        val target = breadcrumbs[index]
        when (target) {
            "Dashboard" -> onNavigate("dashboard")
            "HR" -> onNavigate("module/hr")
            "Finance" -> onNavigate("module/finance")
            "CRM" -> onNavigate("module/crm")
        }
    }

    // THEME DRAWER
    ModalNavigationDrawer(
        drawerState = themeDrawerState,
        drawerContent = {
            ThemeDrawer(
                drawerState = themeDrawerState,
                currentTheme = themeName,
                darkTheme = darkTheme,
                onThemeSelected = onThemeChange,
                onDarkModeToggle = onDarkThemeChange
            )
        }
    ) {

        // MAIN NAVIGATION DRAWER
        BAUNavigationDrawer(
            drawerState = drawerState,
            currentRoute = currentRoute,
            onNavigate = {
                scope.launch { drawerState.close() }
                onNavigate(it)
            }
        ) {

            Scaffold(
                topBar = {
                    Column {
                        BAUTopBar(
                            title = title,
                            onMenuClick = { scope.launch { drawerState.open() } },
                            onThemeClick = { scope.launch { themeDrawerState.open() } }
                        )
                    }
                },
                bottomBar = {
                    Surface(
                        color = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        tonalElevation = 0.dp,
                        shadowElevation = 0.dp
                    ) {
                        BreadcrumbBar(
                            crumbs = breadcrumbs,
                            onCrumbClick = onBreadcrumbNavigate
                        )
                    }
                },
                contentWindowInsets = WindowInsets.safeDrawing
            ) { padding ->
                Box(modifier = Modifier.padding(padding)) {
                    content()
                }
            }
        }
    }
}