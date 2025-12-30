package com.example.businessasusualandroid.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)

val navigationItems = listOf(
    NavigationItem("dashboard", "Dashboard", Icons.Filled.Dashboard),
    NavigationItem("module/hr", "HR", Icons.Filled.People),
    NavigationItem("module/finance", "Finance", Icons.Filled.AttachMoney),
    NavigationItem("module/crm", "CRM", Icons.Filled.BusinessCenter)
)