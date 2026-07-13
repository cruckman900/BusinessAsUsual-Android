package work.businessasusual.data.remote.dto

import kotlinx.serialization.Serializable

/**
 * Mirrors the backend ModuleRegistry ModuleDto returned by GET /api/modules/mobile.
 * Unknown fields are tolerated by the Json { ignoreUnknownKeys = true } config.
 */
@Serializable
data class ModuleDiscoveryDto(
    val moduleId: String = "",
    val key: String = "",
    val displayName: String = "",
    val description: String = "",
    val version: String = "",
    val apiBaseUrl: String = "",
    val uiEntryPoint: String? = null,
    val icon: String? = null,
    val isActive: Boolean = true,
    val permissions: List<String> = emptyList(),
    val capabilities: List<String> = emptyList(),
    val navigationItems: List<ModuleNavigationItemDto> = emptyList(),
)

@Serializable
data class ModuleNavigationItemDto(
    val label: String = "",
    val route: String = "",
    val icon: String? = null,
    val children: List<ModuleNavigationItemDto>? = null,
    val expandedByDefault: Boolean = true,
)
