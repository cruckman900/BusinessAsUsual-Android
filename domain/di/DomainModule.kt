package com.businessasusual.domain.di

import com.businessasusual.domain.usecase.*
import org.koin.dsl.module

val domainModule = module {
    factory { GetModuleUiUseCase(get()) }
    factory { GetNavigationUseCase(get()) }
    factory { GetEmployeeListSpecUseCase(get()) }
    factory { GetEmployeeDetailSpecUseCase(get()) }
    factory { GetEmployeeFormSpecUseCase(get()) }
}