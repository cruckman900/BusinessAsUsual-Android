package com.example.businessasusualandroid.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.businessasusualandroid.ui.dashboard.DashboardScreen
import com.example.businessasusualandroid.ui.hr.HrScreen
import com.example.businessasusualandroid.ui.finance.FinanceScreen
import com.example.businessasusualandroid.ui.crm.CrmScreen
import com.example.domain.model.Module

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController, startDestination = "dashboard") {

        composable("dashboard") {
            DashboardScreen(
                onModuleClick = { module ->
                    navController.navigate("module/${module.id}")
                }
            )
        }

        composable("module/{moduleId}") { backStackEntry ->
            when (backStackEntry.arguments?.getString("moduleId")) {
                "hr" -> HrScreen()
                "finance" -> FinanceScreen()
                "crm" -> CrmScreen()
                else -> DashboardScreen(onModuleClick = { module ->
                    navController.navigate("module/${module.id}")
                })
            }
        }
    }
}