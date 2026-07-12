package work.businessasusual.data.repository

import work.businessasusual.data.datasource.ModuleDataSource
import work.businessasusual.domain.model.Module
import work.businessasusual.domain.repository.ModuleRepository

class ModuleRepositoryImpl(
    private val dataSource: ModuleDataSource
) : ModuleRepository {

    override suspend fun getModules(): List<Module> {
        return dataSource.getModules()
    }
}