package com.businessasusual.domain.usecase

import com.businessasusual.domain.model.*
import com.businessasusual.domain.repository.MobileUiRepository
import com.businessasusual.domain.util.Resource

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