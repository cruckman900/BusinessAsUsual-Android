package work.businessasusual.domain.repository

import work.businessasusual.domain.model.*
import work.businessasusual.domain.util.Resource

interface MobileUiRepository {
	suspend fun getModuleUi(moduleId: String): Resource<ModuleUi>              // composite: nav + all screens
	suspend fun getNavigation(moduleId: String): Resource<NavigationMap>
	suspend fun getEmployeeListSpec(moduleId: String): Resource<ListScreenSpec>
	suspend fun getEmployeeDetailSpec(moduleId: String): Resource<DetailScreenSpec>
	suspend fun getEmployeeFormSpec(moduleId: String): Resource<FormScreenSpec>
	suspend fun getScreenData(moduleId: String, screenKey: String): Resource<List<Map<String, String>>>
}
