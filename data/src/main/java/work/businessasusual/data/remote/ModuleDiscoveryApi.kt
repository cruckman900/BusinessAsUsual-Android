package work.businessasusual.data.remote

import work.businessasusual.data.remote.dto.ModuleDiscoveryDto
import retrofit2.http.GET

/**
 * Module discovery against the ModuleRegistry service.
 * Returns modules the backend advertises as mobile-enabled.
 */
interface ModuleDiscoveryApi {
    @GET("api/modules/mobile")
    suspend fun getMobileModules(): List<ModuleDiscoveryDto>
}
