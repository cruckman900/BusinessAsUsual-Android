package work.businessasusual.ui.mobileui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Resolves a backend-supplied Material icon name to a Compose [ImageVector], giving the Android
 * app 1-to-1 icon parity with the MudBlazor web app (both draw from Google's Material icon set).
 *
 * The web app uses PascalCase names (e.g. `PersonSearch`), while the mobile UI contract emits the
 * same icons in Material's canonical snake_case (e.g. `person_search`). Because the app depends on
 * `androidx.compose.material:material-icons-extended` (the full set), we can resolve virtually every
 * icon by reflection instead of hand-maintaining a giant lookup table:
 *
 *   snake_case name  ->  PascalCase  ->  Icons.Filled.<Name> accessor
 *
 * A small [aliases] map handles common shorthands / naming quirks, and results are cached so the
 * reflection cost is paid at most once per distinct icon name.
 */
object MaterialIconResolver {

	private val cache = HashMap<String, ImageVector>()

	/** Fast-path / disambiguation aliases where the contract name differs from the Material id. */
	private val aliases: Map<String, String> = mapOf(
		"people" to "People",
		"person_search" to "PersonSearch",
		"corporate_fare" to "CorporateFare",
		"beach_access" to "BeachAccess",
		"workspace_premium" to "WorkspacePremium",
		"health_and_safety" to "HealthAndSafety",
		"bar_chart" to "BarChart",
		"add_business" to "AddBusiness",
		"add_circle" to "AddCircle",
		"person_add" to "PersonAdd",
		"check" to "Check",
		"check_circle" to "CheckCircle",
		"save" to "Save",
		"cancel" to "Cancel",
		"block" to "Block",
		"schedule" to "Schedule",
		"event" to "Event",
		"flag" to "Flag",
		"school" to "School",
		"payments" to "Payments",
		"assessment" to "Assessment",
		"visibility" to "Visibility",
		"approval" to "Approval",
		"verified" to "Verified",
		"mail" to "Mail",
		"pending" to "Pending",
		"help" to "Help",
		"arrow_back" to "ArrowBack",
	)

	/** Fallback used when a name cannot be resolved to a real Material icon. */
	private val fallback: ImageVector = Icons.Filled.Info

	fun resolve(rawName: String?): ImageVector {
		val name = rawName?.trim().orEmpty()
		if (name.isEmpty()) return fallback
		cache[name]?.let { return it }

		val pascal = aliases[name] ?: toPascalCase(name)
		val vector = lookup(pascal) ?: fallback
		cache[name] = vector
		return vector
	}

	/** Converts "person_search" / "person-search" / "personSearch" -> "PersonSearch". */
	private fun toPascalCase(name: String): String =
		name.split('_', '-', ' ')
			.filter { it.isNotEmpty() }
			.joinToString("") { part ->
				part.replaceFirstChar { it.uppercaseChar() }
			}

	/**
	 * Reflectively reads the `Icons.Filled.<Name>` icon from the material-icons-extended library.
	 * Compose compiles each icon as a top-level extension property, e.g.
	 *   `val Icons.Filled.PersonSearch: ImageVector`
	 * which lands in a generated class `androidx.compose.material.icons.filled.PersonSearchKt`
	 * exposing a static `getPersonSearch(Icons.Filled)` method. Filled matches the web app's
	 * `Icons.Material.Filled.*`.
	 */
	private fun lookup(pascalName: String): ImageVector? = try {
		val clazz = Class.forName("androidx.compose.material.icons.filled.${pascalName}Kt")
		val getter = clazz.getMethod("get$pascalName", Icons.Filled::class.java)
		getter.invoke(null, Icons.Filled) as? ImageVector
	} catch (_: Throwable) {
		null
	}
}
