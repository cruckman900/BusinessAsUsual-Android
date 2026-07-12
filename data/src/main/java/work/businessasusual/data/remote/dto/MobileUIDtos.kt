package work.businessasusual.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MobileUiSpecDto(
	val moduleId: String = "",
	val moduleName: String = "",
	val version: String = "",
	val navigation: NavigationMapDto = NavigationMapDto(),
	val screens: ScreensDto = ScreensDto(),
)

@Serializable
data class ScreensDto(
	@SerialName("employee-list") val employeeList: EmployeeListSpecDto? = null,
	@SerialName("employee-detail") val employeeDetail: EmployeeDetailSpecDto? = null,
	@SerialName("employee-form") val employeeForm: EmployeeFormSpecDto? = null,
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
