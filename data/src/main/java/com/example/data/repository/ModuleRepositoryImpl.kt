package com.example.data.repository

import com.example.data.datasource.FakeModuleDataSource
import com.example.domain.model.Module
import com.example.domain.repository.ModuleRepository

class ModuleRepositoryImpl(
    private val dataSource: FakeModuleDataSource
) : ModuleRepository {

    override suspend fun getModules(): List<Module> {
        return dataSource.getModules()
    }
}