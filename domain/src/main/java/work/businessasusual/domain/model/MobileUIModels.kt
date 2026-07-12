package work.businessasusual.domain.model

/** Aggregated UI contract for one module (e.g. HR), fetched in a single call. */
data class ModuleUi(
	val moduleId: String,
	val moduleName: String,
	val version: String,
	val navigation: NavigationMap,
	val listScreen: ListScreenSpec? = null,
	val detailScreen: DetailScreenSpec? = null,
	val formScreen: FormScreenSpec? = null,
)

data class NavigationMap(
	val moduleId: String,
	val moduleName: String,
	val icon: String,
	val items: List<NavItem>,
)

data class NavItem(
	val id: String,
	val label: String,
	val icon: String,
	val screen: String,
	val route: String? = null,
	val children: List<NavItem> = emptyList(),
	val requiresPermission: Boolean = false,
	val permission: String? = null,
)

// ---- List screen ----
data class ListScreenSpec(
	val title: String,
	val searchPlaceholder: String,
	val enableSearch: Boolean,
	val enableFilter: Boolean,
	val columns: List<ListColumn>,
	val actions: List<ScreenAction>,
	val filters: List<Filter>,
	val emptyStateMessage: String,
)

data class ListColumn(
	val name: String,
	val label: String,
	val type: String,
	val sortable: Boolean,
	val width: Int,
)

data class Filter(val id: String, val label: String, val type: String, val values: List<FilterValue>)
data class FilterValue(val id: String, val label: String, val value: String)

// ---- Detail screen ----
data class DetailScreenSpec(
	val title: String,
	val sections: List<DetailSection>,
	val actions: List<ScreenAction>,
)

data class DetailSection(
	val id: String,
	val title: String,
	val fields: List<DetailField>,
	val collapsible: Boolean = false,
	val defaultCollapsed: Boolean = false,
)

data class DetailField(
	val name: String,
	val label: String,
	val type: String,
	val readOnly: Boolean = true,
	val icon: String? = null,
	val format: String? = null,
)

// ---- Form screen ----
data class FormScreenSpec(
	val title: String,
	val sections: List<FormSection>,
	val actions: List<ScreenAction>,
	val validation: FormValidation,
)

data class FormSection(val id: String, val title: String, val fields: List<FormField>)

data class FormField(
	val name: String,
	val label: String,
	val type: String,
	val required: Boolean = false,
	val placeholder: String? = null,
	val helpText: String? = null,
	val options: List<SelectOption> = emptyList(),
	val maxLength: Int? = null,
	val minLength: Int? = null,
	val pattern: String? = null,
	val validationMessage: String? = null,
)

data class SelectOption(val value: String, val label: String)

data class FormValidation(
	val errorMessages: Map<String, String> = emptyMap(),
	val customValidationEndpoint: String? = null,
)

// ---- Shared action ----
data class ScreenAction(
	val id: String,
	val label: String,
	val icon: String,
	val action: String,
	val navigateTo: String? = null,
	val apiEndpoint: String? = null,
	val requiresConfirmation: Boolean = false,
	val confirmationMessage: String? = null,
)

object ActionTypes {
	const val NAVIGATE = "navigate"
	const val API_CALL = "api-call"
	const val CUSTOM = "custom"
}

object FieldTypes {
	const val TEXT = "text"; const val EMAIL = "email"; const val PHONE = "phone"
	const val NUMBER = "number"; const val DATE = "date"; const val SELECT = "select"
	const val MULTISELECT = "multiselect"; const val IMAGE = "image"; const val BADGE = "badge"
}
