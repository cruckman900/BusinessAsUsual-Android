package work.businessasusual.domain.model

/**
 * A discoverable business module. [icon] is a backend-provided icon key
 * (e.g. "hr", "finance", "people") resolved to a drawable at render time,
 * with a safe fallback for unknown modules.
 */
data class Module(
    val id: String,
    val name: String,
    val description: String,
    val icon: String,
    val route: String,
)
