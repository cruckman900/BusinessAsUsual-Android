package com.example.domain.di

import com.example.domain.usecase.GetModulesUseCase
import com.example.domain.usecase.GetHrActionsUseCase
import org.koin.dsl.module

val domainModule = module {

    // Module list for dashboard
    factory { GetModulesUseCase(get()) }

    // Module list for HR
    factory { GetHrActionsUseCase(get()) }
}