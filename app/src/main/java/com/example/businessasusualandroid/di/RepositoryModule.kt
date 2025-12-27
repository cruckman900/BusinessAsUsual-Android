package com.example.businessasusualandroid.di

import com.example.data.datasource.FakeHrDataSource
import com.example.data.datasource.FakeModuleDataSource
import com.example.data.repository.HrRepositoryImpl
import com.example.data.repository.ModuleRepositoryImpl
import com.example.domain.repository.HrRepository
import com.example.domain.repository.ModuleRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providedModuleRepository(): ModuleRepository =
        ModuleRepositoryImpl(FakeModuleDataSource())

    @Provides
    @Singleton
    fun provideHrRepository(): HrRepository =
        HrRepositoryImpl(FakeHrDataSource())
}