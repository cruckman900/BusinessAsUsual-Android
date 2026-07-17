package work.businessasusual.data.mapper

import work.businessasusual.data.remote.dto.*
import work.businessasusual.domain.model.*

fun MobileUiSpecDto.toDomain() = ModuleUi(
	moduleId = moduleId,
	moduleName = moduleName,
	displayName = displayName.ifBlank { moduleName },
	version = version,
	navigation = navigation.toDomain(),
	screens = screens.mapNotNull { (key, dto) -> dto.toDomain()?.let { key to it } }.toMap(),
)

/** Maps a union screen DTO to a typed [ScreenSpec] based on its discriminator. */
fun ScreenDto.toDomain(): ScreenSpec? = when (type) {
	"list" -> ListScreenSpec(
		title, searchPlaceholder, enableSearch, enableFilter,
		columns.map { it.toDomain() }, actions.map { it.toDomain() },
		filters.map { it.toDomain() }, emptyStateMessage,
		stats.map { it.toDomain() },
	)
	"timeline" -> TimelineScreenSpec(
		title, searchPlaceholder, enableSearch,
		stats.map { it.toDomain() }, itemFields.toDomain(),
		actions.map { it.toDomain() }, emptyStateMessage,
	)
	"detail" -> DetailScreenSpec(
		title, sections.map { it.toDetailSection() }, actions.map { it.toDomain() },
	)
	"form" -> FormScreenSpec(
		title, sections.map { it.toFormSection() }, actions.map { it.toDomain() }, validation.toDomain(),
	)
	"chart" -> ChartScreenSpec(
		title, charts.map { it.toDomain() }, emptyStateMessage,
	)
	"board" -> BoardScreenSpec(
		title = title,
		searchPlaceholder = searchPlaceholder,
		enableSearch = enableSearch,
		groupByField = groupByField,
		columns = columns.map { it.toBoardColumn() },
		cardLayout = cardLayout.toBoardCardLayout(),
		actions = actions.map { it.toDomain() },
		enableDragToMove = enableDragToMove,
		moveEndpoint = moveEndpoint,
		emptyStateMessage = emptyStateMessage,
		fallbackColumns = fallbackColumns.map { it.toDomain() },
	)
	"card-collection" -> CardCollectionScreenSpec(
		title = title,
		searchPlaceholder = searchPlaceholder,
		enableSearch = enableSearch,
		enableFilter = enableFilter,
		preferredColumns = preferredColumns,
		cardLayout = cardLayout.toCardLayout(),
		actions = actions.map { it.toDomain() },
		cardActions = cardActions.map { it.toDomain() },
		filters = filters.map { it.toDomain() },
		emptyStateMessage = emptyStateMessage,
		fallbackColumns = fallbackColumns.map { it.toDomain() },
	)
	else -> null
}

fun ColumnDefinitionDto.toBoardColumn() = BoardColumn(
	id = id.ifBlank { name }, label = label, color = color, summaryLabel = summaryLabel,
)
fun CardLayoutDto.toBoardCardLayout() = BoardCardLayout(
	titleField = titleField, subtitleField = subtitleField, valueField = valueField,
	progressField = progressField, badgeField = badgeField, metaField = metaField,
)
fun CardLayoutDto.toCardLayout() = CardLayout(
	titleField = titleField, subtitleField = subtitleField, previewField = previewField,
	badgeField = badgeField, statusField = statusField, metaField = metaField, iconField = iconField,
)

fun ChartSpecDto.toDomain() = ChartSpec(
	id = id, title = title, subtitle = subtitle, chartType = chartType,
	labels = labels, series = series.map { it.toDomain() },
)
fun ChartSeriesDto.toDomain() = ChartSeries(name, color, points.map { it.toDomain() })
fun ChartDataPointDto.toDomain() = ChartDataPoint(label, value, color)

fun StatCardDto.toDomain() = StatCard(id, label, value, icon, tone)
fun TimelineItemFieldMapDto.toDomain() = TimelineItemFields(
	titleField, subtitleField, descriptionField, timestampField,
	statusField, typeField, ownerField, iconField,
)

fun ScreenSectionDto.toDetailSection() =
	DetailSection(id, title, fields.map { it.toDetailField() }, collapsible, defaultCollapsed)
fun ScreenFieldDto.toDetailField() = DetailField(name, label, type, readOnly, icon, format)

fun ScreenSectionDto.toFormSection() = FormSection(id, title, fields.map { it.toFormField() })
fun ScreenFieldDto.toFormField() = FormField(
	name, label, type, required, placeholder, helpText,
	options?.map { it.toDomain() } ?: emptyList(),
	maxLength, minLength, pattern, validationMessage,
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


