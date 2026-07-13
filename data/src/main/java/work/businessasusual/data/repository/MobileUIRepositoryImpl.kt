package work.businessasusual.data.repository

import work.businessasusual.data.mapper.toDomain
import work.businessasusual.data.remote.MobileUiApi
import work.businessasusual.domain.model.*
import work.businessasusual.domain.repository.MobileUiRepository
import work.businessasusual.domain.util.Resource

class MobileUiRepositoryImpl(
private val api: MobileUiApi,
) : MobileUiRepository {

override suspend fun getModuleUi(moduleId: String): Resource<ModuleUi> =
safeCall { api.getUiSpec(moduleId).toDomain() }

override suspend fun getNavigation(moduleId: String): Resource<NavigationMap> =
safeCall { api.getNavigation(moduleId).toDomain() }

override suspend fun getEmployeeListSpec(moduleId: String): Resource<ListScreenSpec> =
safeCall { api.getEmployeeListSpec(moduleId).toDomain() }

override suspend fun getEmployeeDetailSpec(moduleId: String): Resource<DetailScreenSpec> =
safeCall { api.getEmployeeDetailSpec(moduleId).toDomain() }

override suspend fun getEmployeeFormSpec(moduleId: String): Resource<FormScreenSpec> =
	safeCall { api.getEmployeeFormSpec(moduleId).toDomain() }

override suspend fun getScreenData(moduleId: String, screenKey: String): Resource<List<Map<String, String>>> =
	safeCall { api.getScreenData(moduleId, screenKey) }

private suspend fun <T> safeCall(block: suspend () -> T): Resource<T> = try {
Resource.Success(block())
} catch (e: Exception) {
Resource.Error(e.message ?: "Unexpected error", e)
}
}
