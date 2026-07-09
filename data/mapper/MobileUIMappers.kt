package com.businessasusual.data.mapper

import com.businessasusual.data.remote.dto.*
import com.businessasusual.domain.model.*

fun MobileUiSpecDto.toDomain() = ModuleUi(
    moduleId = moduleId,
    moduleName = moduleName,
    version = version,
    navigation = navigation.toDomain(),
    listScreen = screens.employeeList?.toDomain(),
    detailScreen = screens.employeeDetail?.toDomain(),
    formScreen = screens.employeeForm?.toDomain(),
)

fun NavigationMapDto.toDomain() = NavigationMap(moduleId, moduleName, icon, items.map { it.toDomain() })

fun NavigationItemDto.toDomain(): NavItem = NavItem(
    id = id, label = label, icon = icon, screen = screen, route = route,
    children = children?.map { it.toDomain() } ?: emptyList(),
    requiresPermission = requiresPermission, permission = permission,
)

fun EmployeeListSpecDto.toDomain() = ListScreenSpec(
    title, searchPlaceholder, enableSearch, enableFilter,
    columns.map { it.toDomain() }, actions.map { it.toDomain() },
    filters.map { it.toDomain() }, emptyStateMessage,
)
fun ColumnDefinitionDto.toDomain() = ListColumn(name, label, type, sortable, width)
fun FilterOptionDto.toDomain() = Filter(id, label, type, values.map { it.toDomain() })
fun FilterValueDto.toDomain() = FilterValue(id, label, value)

fun EmployeeDetailSpecDto.toDomain() =
    DetailScreenSpec(title, sections.map { it.toDomain() }, actions.map { it.toDomain() })
fun SectionDefinitionDto.toDomain() =
    DetailSection(id, title, fields.map { it.toDomain() }, collapsible, defaultCollapsed)
fun FieldDefinitionDto.toDomain() = DetailField(name, label, type, readOnly, icon, format)

fun EmployeeFormSpecDto.toDomain() =
    FormScreenSpec(title, sections.map { it.toDomain() }, actions.map { it.toDomain() }, validation.toDomain())
fun FormSectionDefinitionDto.toDomain() = FormSection(id, title, fields.map { it.toDomain() })
fun FormFieldDefinitionDto.toDomain() = FormField(
    name, label, type, required, placeholder, helpText,
    options?.map { it.toDomain() } ?: emptyList(),
    maxLength, minLength, pattern, validationMessage,
)
fun SelectOptionDto.toDomain() = SelectOption(value, label)
fun ValidationRulesDto.toDomain() = FormValidation(errorMessages, customValidationEndpoint)

fun ActionButtonDto.toDomain() = ScreenAction(
    id, label, icon, action, navigateTo, apiEndpoint, requiresConfirmation, confirmationMessage,
)