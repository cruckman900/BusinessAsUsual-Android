package work.businessasusual.di

import work.businessasusual.ui.dashboard.DashboardViewModel
import work.businessasusual.ui.hr.HrViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // Dashboard
    viewModel { DashboardViewModel(getModulesUseCase = get()) }

    // Hr module
    viewModel { HrViewModel(getHrActionsUseCase = get()) }
}