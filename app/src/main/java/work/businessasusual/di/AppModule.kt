package work.businessasusual.di

import work.businessasusual.ui.dashboard.DashboardViewModel
import work.businessasusual.ui.mobileui.MobileUiViewModel
import work.businessasusual.ui.navigation.NavigationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // Dashboard
    viewModel { DashboardViewModel(getModulesUseCase = get()) }

    // Navigation drawer (backend-driven menu)
    viewModel { NavigationViewModel(getModulesUseCase = get()) }

    // Contract-driven module UI host (moduleId supplied at navigation time)
    viewModel { (moduleId: String) -> MobileUiViewModel(moduleId, get()) }
}
