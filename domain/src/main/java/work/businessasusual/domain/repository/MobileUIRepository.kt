package work.businessasusual.domain.repository

import work.businessasusual.domain.model.*
import work.businessasusual.domain.util.Resource

interface MobileUiRepository {
	suspend fun getModuleUi(): Resource<ModuleUi>              // composite: nav + all screens
	suspend fun getNavigation(): Resource<NavigationMap>
	suspend fun getEmployeeListSpec(): Resource<ListScreenSpec>
	suspend fun getEmployeeDetailSpec(): Resource<DetailScreenSpec>
	suspend fun getEmployeeFormSpec(): Resource<FormScreenSpec>
}
