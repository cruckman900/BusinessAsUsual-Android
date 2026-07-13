package work.businessasusual.data.remote

import work.businessasusual.data.remote.dto.*
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Per-module mobile UI contract endpoints. The {moduleKey} path segment lets a
 * single API serve every backend-discovered module (e.g. "hr", "finance", "crm").
 */
interface MobileUiApi {
@GET("api/{moduleKey}/mobile/ui-spec")
suspend fun getUiSpec(@Path("moduleKey") moduleKey: String): MobileUiSpecDto

@GET("api/{moduleKey}/mobile/navigation")
suspend fun getNavigation(@Path("moduleKey") moduleKey: String): NavigationMapDto

@GET("api/{moduleKey}/mobile/ui-spec/employee-list")
suspend fun getEmployeeListSpec(@Path("moduleKey") moduleKey: String): EmployeeListSpecDto

@GET("api/{moduleKey}/mobile/ui-spec/employee-detail")
suspend fun getEmployeeDetailSpec(@Path("moduleKey") moduleKey: String): EmployeeDetailSpecDto

	@GET("api/{moduleKey}/mobile/ui-spec/employee-form")
	suspend fun getEmployeeFormSpec(@Path("moduleKey") moduleKey: String): EmployeeFormSpecDto

	/** Row data for a list screen; each row is keyed by the screen's column names. */
	@GET("api/{moduleKey}/mobile/data/{screenKey}")
	suspend fun getScreenData(
		@Path("moduleKey") moduleKey: String,
		@Path("screenKey") screenKey: String,
	): List<Map<String, String>>
}
