package com.example.businessasusualandroid.ui.crm

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun CrmScreen(
    navController: NavHostController,
    onNavigate: (String) -> Unit,
    themeName: String,
    darkTheme: Boolean,
    onThemeChange: (String) -> Unit,
    onDarkThemeChange: (Boolean) -> Unit,
    ) {
    Text("CRM Module Coming Soon")
}