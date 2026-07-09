package com.businessasusual.data.remote

import com.businessasusual.data.remote.dto.*
import retrofit2.http.GET

interface MobileUiApi {
    @GET("api/hr/mobile/ui-spec")
    suspend fun getUiSpec(): MobileUiSpecDto

    @GET("api/hr/mobile/navigation")
    suspend fun getNavigation(): NavigationMapDto

    @GET("api/hr/mobile/ui-spec/employee-list")
    suspend fun getEmployeeListSpec(): EmployeeListSpecDto

    @GET("api/hr/mobile/ui-spec/employee-detail")
    suspend fun getEmployeeDetailSpec(): EmployeeDetailSpecDto

    @GET("api/hr/mobile/ui-spec/employee-form")
    suspend fun getEmployeeFormSpec(): EmployeeFormSpecDto
}