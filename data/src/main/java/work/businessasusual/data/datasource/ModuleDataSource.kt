package work.businessasusual.data.datasource

import work.businessasusual.domain.model.Module

interface ModuleDataSource {
    fun getModules(): List<Module>
}