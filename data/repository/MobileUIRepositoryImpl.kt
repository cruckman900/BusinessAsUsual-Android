package com.businessasusual.data.repository

import com.businessasusual.data.mapper.toDomain
import com.businessasusual.data.remote.MobileUiApi
import com.businessasusual.domain.model.*
import com.businessasusual.domain.repository.MobileUiRepository
import com.businessasusual.domain.util.Resource

class MobileUiRepositoryImpl(
    private val api: MobileUiApi,
) : MobileUiRepository {

    override suspend fun getModuleUi(): Resource<ModuleUi> =
        safeCall { api.getUiSpec().toDomain() }

    override suspend fun getNavigation(): Resource<NavigationMap> =
        safeCall { api.getNavigation().toDomain() }

    override suspend fun getEmployeeListSpec(): Resource<ListScreenSpec> =
        safeCall { api.getEmployeeListSpec().toDomain() }

    override suspend fun getEmployeeDetailSpec(): Resource<DetailScreenSpec> =
        safeCall { api.getEmployeeDetailSpec().toDomain() }

    override suspend fun getEmployeeFormSpec(): Resource<FormScreenSpec> =
        safeCall { api.getEmployeeFormSpec().toDomain() }

    private suspend fun <T> safeCall(block: suspend () -> T): Resource<T> = try {
        Resource.Success(block())
    } catch (e: Exception) {
        Resource.Error(e.message ?: "Unexpected error", e)
    }
}