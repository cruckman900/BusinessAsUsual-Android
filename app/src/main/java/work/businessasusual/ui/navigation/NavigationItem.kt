package work.businessasusual.ui.navigation

import work.businessasusual.domain.model.Module

/**
 * A drawer/menu entry. [iconKey] is a backend-provided icon key resolved to a
 * drawable at render time. Menu entries are built from discovered modules, not
 * hard-coded.
 */
data class NavigationItem(
    val route: String,
    val label: String,
    val iconKey: String,
)

/** Static Dashboard entry that always heads the menu. */
val dashboardNavigationItem = NavigationItem(
    route = "dashboard",
    label = "Dashboard",
    iconKey = "dashboard",
)

/** Builds the menu from discovered modules, with Dashboard always first. */
fun buildNavigationItems(modules: List<Module>): List<NavigationItem> =
    listOf(dashboardNavigationItem) + modules.map { module ->
        NavigationItem(
            route = module.route,
            label = module.name,
            iconKey = module.icon,
        )
    }
