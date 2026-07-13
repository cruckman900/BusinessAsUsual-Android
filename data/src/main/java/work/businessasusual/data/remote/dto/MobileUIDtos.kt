package work.businessasusual.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class MobileUiSpecDto(
	val moduleId: String = "",
	val moduleName: String = "",
	val version: String = "",
	val navigation: NavigationMapDto = NavigationMapDto(),
	val screens: Map<String, ScreenDto> = emptyMap(),
)

/**
 * Union DTO for any backend screen. The [type] discriminator selects which
 * fields are relevant ("list" | "detail" | "form"); all others stay null/empty.
 */
@Serializable
data class ScreenDto(
	val type: String = "",
	val title: String = "",
	// list
	val searchPlaceholder: String = "",
	val enableSearch: Boolean = true,
	val enableFilter: Boolean = true,
	val columns: List<ColumnDefinitionDto> = emptyList(),
	val filters: List<FilterOptionDto> = emptyList(),
	val emptyStateMessage: String = "",
	// detail + form
	val sections: List<ScreenSectionDto> = emptyList(),
	// form
	val validation: ValidationRulesDto = ValidationRulesDto(),
	// chart
	val charts: List<ChartSpecDto> = emptyList(),
	// shared
	val actions: List<ActionButtonDto> = emptyList(),
)

@Serializable
data class ChartSpecDto(
	val id: String = "",
	val title: String = "",
	val subtitle: String? = null,
	val chartType: String = "line",
	val labels: List<String> = emptyList(),
	val series: List<ChartSeriesDto> = emptyList(),
)

@Serializable
data class ChartSeriesDto(
	val name: String = "",
	val color: String? = null,
	val points: List<ChartDataPointDto> = emptyList(),
)

@Serializable
data class ChartDataPointDto(
	val label: String = "",
	val value: Double = 0.0,
	val color: String? = null,
)

/** Union section covering both detail and form sections. */
@Serializable
data class ScreenSectionDto(
	val id: String = "",
	val title: String = "",
	val fields: List<ScreenFieldDto> = emptyList(),
	val collapsible: Boolean = false,
	val defaultCollapsed: Boolean = false,
)

/** Union field covering both detail and form field shapes. */
@Serializable
data class ScreenFieldDto(
	val name: String = "",
	val label: String = "",
	val type: String = "text",
	// detail
	val readOnly: Boolean = true,
	val icon: String? = null,
	val format: String? = null,
	// form
	val required: Boolean = false,
	val placeholder: String? = null,
	val helpText: String? = null,
	val options: List<SelectOptionDto>? = null,
	val maxLength: Int? = null,
	val minLength: Int? = null,
	val pattern: String? = null,
	val validationMessage: String? = null,
)

@Serializable
data class NavigationMapDto(
	val moduleId: String = "",
	val moduleName: String = "",
	val icon: String = "",
	val items: List<NavigationItemDto> = emptyList(),
)

@Serializable
data class NavigationItemDto(
	val id: String = "",
	val label: String = "",
	val icon: String = "",
	val screen: String = "",
	val route: String? = null,
	val children: List<NavigationItemDto>? = null,
	val requiresPermission: Boolean = false,
	val permission: String? = null,
)

@Serializable
data class EmployeeListSpecDto(
	val title: String = "",
	val searchPlaceholder: String = "",
	val enableSearch: Boolean = true,
	val enableFilter: Boolean = true,
	val columns: List<ColumnDefinitionDto> = emptyList(),
	val actions: List<ActionButtonDto> = emptyList(),
	val filters: List<FilterOptionDto> = emptyList(),
	val emptyStateMessage: String = "",
)

@Serializable
data class ColumnDefinitionDto(
	val name: String = "",
	val label: String = "",
	val type: String = "text",
	val sortable: Boolean = false,
	val width: Int = 100,
)

@Serializable
data class ActionButtonDto(
	val id: String = "",
	val label: String = "",
	val icon: String = "",
	val action: String = "",
	val navigateTo: String? = null,
	val apiEndpoint: String? = null,
	val requiresConfirmation: Boolean = false,
	val confirmationMessage: String? = null,
)

@Serializable
data class FilterOptionDto(
	val id: String = "",
	val label: String = "",
	val type: String = "select",
	val values: List<FilterValueDto> = emptyList(),
)

@Serializable
data class FilterValueDto(val id: String = "", val label: String = "", val value: String = "")

@Serializable
data class EmployeeDetailSpecDto(
	val title: String = "",
	val sections: List<SectionDefinitionDto> = emptyList(),
	val actions: List<ActionButtonDto> = emptyList(),
)

@Serializable
data class SectionDefinitionDto(
	val id: String = "",
	val title: String = "",
	val fields: List<FieldDefinitionDto> = emptyList(),
	val collapsible: Boolean = false,
	val defaultCollapsed: Boolean = false,
)

@Serializable
data class FieldDefinitionDto(
	val name: String = "",
	val label: String = "",
	val type: String = "text",
	val readOnly: Boolean = true,
	val icon: String? = null,
	val format: String? = null,
)

@Serializable
data class EmployeeFormSpecDto(
	val title: String = "",
	val sections: List<FormSectionDefinitionDto> = emptyList(),
	val actions: List<ActionButtonDto> = emptyList(),
	val validation: ValidationRulesDto = ValidationRulesDto(),
)

@Serializable
data class FormSectionDefinitionDto(
	val id: String = "",
	val title: String = "",
	val fields: List<FormFieldDefinitionDto> = emptyList(),
)

@Serializable
data class FormFieldDefinitionDto(
	val name: String = "",
	val label: String = "",
	val type: String = "text",
	val required: Boolean = false,
	val placeholder: String? = null,
	val helpText: String? = null,
	val options: List<SelectOptionDto>? = null,
	val maxLength: Int? = null,
	val minLength: Int? = null,
	val pattern: String? = null,
	val validationMessage: String? = null,
)

@Serializable
data class SelectOptionDto(val value: String = "", val label: String = "")

@Serializable
data class ValidationRulesDto(
	val errorMessages: Map<String, String> = emptyMap(),
	val customValidationEndpoint: String? = null,
)
