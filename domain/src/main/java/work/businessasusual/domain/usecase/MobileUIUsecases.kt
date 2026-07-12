package work.businessasusual.domain.usecase

import work.businessasusual.domain.model.*
import work.businessasusual.domain.repository.MobileUiRepository
import work.businessasusual.domain.util.Resource

class GetModuleUiUseCase(private val repo: MobileUiRepository) {
	suspend operator fun invoke(): Resource<ModuleUi> = repo.getModuleUi()
}
class GetNavigationUseCase(private val repo: MobileUiRepository) {
	suspend operator fun invoke(): Resource<NavigationMap> = repo.getNavigation()
}
class GetEmployeeListSpecUseCase(private val repo: MobileUiRepository) {
	suspend operator fun invoke(): Resource<ListScreenSpec> = repo.getEmployeeListSpec()
}
class GetEmployeeDetailSpecUseCase(private val repo: MobileUiRepository) {
	suspend operator fun invoke(): Resource<DetailScreenSpec> = repo.getEmployeeDetailSpec()
}
class GetEmployeeFormSpecUseCase(private val repo: MobileUiRepository) {
	suspend operator fun invoke(): Resource<FormScreenSpec> = repo.getEmployeeFormSpec()
}
