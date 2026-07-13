package work.businessasusual.data.mapper

import work.businessasusual.data.remote.dto.ModuleDiscoveryDto
import work.businessasusual.domain.model.Module

/**
 * Maps a backend discovery DTO to the domain Module used by the dashboard and menu.
 * Route defaults to "module/{key|moduleId}" so navigation is fully backend-driven.
 */
fun ModuleDiscoveryDto.toDomain(): Module {
    val moduleKey = key.ifBlank { moduleId }
    return Module(
        id = moduleKey,
        name = displayName.ifBlank { moduleKey },
        description = description,
        icon = icon ?: moduleKey,
        route = "module/$moduleKey",
    )
}

fun List<ModuleDiscoveryDto>.toDomain(): List<Module> =
    filter { it.isActive }.map { it.toDomain() }
