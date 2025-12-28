package com.example.businessasusualandroid.di

import com.example.businessasusualandroid.ui.dashboard.DashboardViewModel
import com.example.businessasusualandroid.ui.hr.HrViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // Dashboard
    viewModel { DashboardViewModel(getModulesUseCase = get()) }

    // Hr module
    viewModel { HrViewModel(getHrActionsUseCase = get()) }
}