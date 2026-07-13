package work.businessasusual.domain.usecase

import work.businessasusual.domain.model.*
import work.businessasusual.domain.repository.MobileUiRepository
import work.businessasusual.domain.util.Resource

class GetModuleUiUseCase(private val repo: MobileUiRepository) {
suspend operator fun invoke(moduleId: String): Resource<ModuleUi> = repo.getModuleUi(moduleId)
}
class GetNavigationUseCase(private val repo: MobileUiRepository) {
suspend operator fun invoke(moduleId: String): Resource<NavigationMap> = repo.getNavigation(moduleId)
}
class GetEmployeeListSpecUseCase(private val repo: MobileUiRepository) {
suspend operator fun invoke(moduleId: String): Resource<ListScreenSpec> = repo.getEmployeeListSpec(moduleId)
}
class GetEmployeeDetailSpecUseCase(private val repo: MobileUiRepository) {
suspend operator fun invoke(moduleId: String): Resource<DetailScreenSpec> = repo.getEmployeeDetailSpec(moduleId)
}
class GetEmployeeFormSpecUseCase(private val repo: MobileUiRepository) {
suspend operator fun invoke(moduleId: String): Resource<FormScreenSpec> = repo.getEmployeeFormSpec(moduleId)
}
class GetScreenDataUseCase(private val repo: MobileUiRepository) {
suspend operator fun invoke(moduleId: String, screenKey: String): Resource<List<Map<String, String>>> =
	repo.getScreenData(moduleId, screenKey)
}
