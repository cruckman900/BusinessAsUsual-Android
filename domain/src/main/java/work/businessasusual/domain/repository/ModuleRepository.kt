package work.businessasusual.domain.repository

import work.businessasusual.domain.model.Module

interface ModuleRepository {
    suspend fun getModules(): List<Module>
}