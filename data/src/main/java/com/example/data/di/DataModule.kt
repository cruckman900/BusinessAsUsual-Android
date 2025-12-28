package com.example.data.di

import com.example.data.datasource.FakeHrDataSource
import com.example.data.datasource.FakeModuleDataSource
import com.example.data.datasource.HrDataSource
import com.example.data.datasource.ModuleDataSource
import com.example.data.repository.ModuleRepositoryImpl
import com.example.data.repository.HrRepositoryImpl
import com.example.domain.repository.ModuleRepository
import com.example.domain.repository.HrRepository
import org.koin.dsl.module

val dataModule = module {

    // Data sources
    single<ModuleDataSource> { FakeModuleDataSource() }
    single<HrDataSource> { FakeHrDataSource() }

    // Dashboard module list
    single<ModuleRepository> { ModuleRepositoryImpl(get()) }

    // HR actions
    single<HrRepository> { HrRepositoryImpl(get()) }
}