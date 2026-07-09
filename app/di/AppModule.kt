package com.businessasusual.app.di

import com.businessasusual.app.ui.mobileui.MobileUiViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { MobileUiViewModel(get()) }
}