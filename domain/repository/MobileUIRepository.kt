package com.businessasusual.domain.repository

import com.businessasusual.domain.model.*
import com.businessasusual.domain.util.Resource

interface MobileUiRepository {
    suspend fun getModuleUi(): Resource<ModuleUi>              // composite: nav + all screens
    suspend fun getNavigation(): Resource<NavigationMap>
    suspend fun getEmployeeListSpec(): Resource<ListScreenSpec>
    suspend fun getEmployeeDetailSpec(): Resource<DetailScreenSpec>
    suspend fun getEmployeeFormSpec(): Resource<FormScreenSpec>
}