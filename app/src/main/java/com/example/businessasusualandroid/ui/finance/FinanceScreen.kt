package com.example.businessasusualandroid.ui.finance

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun FinanceScreen(
    navController: NavHostController,
    onNavigate: (String) -> Unit,
    themeName: String,
    darkTheme: Boolean,
    onThemeChange: (String) -> Unit,
    onDarkThemeChange: (Boolean) -> Unit,
) {
    Text("Finance Module Coming Soon")
}