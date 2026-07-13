package work.businessasusual.data.repository

import work.businessasusual.data.mapper.toDomain
import work.businessasusual.data.remote.ModuleDiscoveryApi
import work.businessasusual.domain.model.Module
import work.businessasusual.domain.repository.ModuleRepository

/**
 * Fetches modules from the backend ModuleRegistry discovery endpoint.
 * On failure it returns an empty list so the UI degrades gracefully
 * rather than crashing.
 */
class ModuleRepositoryImpl(
    private val api: ModuleDiscoveryApi
) : ModuleRepository {

    override suspend fun getModules(): List<Module> =
        try {
            api.getMobileModules().toDomain()
        } catch (e: Exception) {
            emptyList()
        }
}
