package work.businessasusual.ui.mobileui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import work.businessasusual.domain.model.*
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun MobileUiScreen(
	moduleId: String,
	onScreenTitleChange: (String?) -> Unit = {},
	viewModel: MobileUiViewModel = koinViewModel { parametersOf(moduleId) },
) {
	val state by viewModel.state.collectAsStateWithLifecycle()
	when (val s = state) {
		MobileUiState.Loading ->
			Box(Modifier.fillMaxSize(), Alignment.Center) { CircularProgressIndicator() }

		is MobileUiState.Error ->
			Column(
				Modifier.fillMaxSize().padding(24.dp),
				verticalArrangement = Arrangement.Center,
				horizontalAlignment = Alignment.CenterHorizontally,
			) {
				Text("Failed to load UI contract", style = MaterialTheme.typography.titleMedium)
				Spacer(Modifier.height(4.dp))
				Text(s.message, style = MaterialTheme.typography.bodyMedium)
				Spacer(Modifier.height(12.dp))
				Button(onClick = viewModel::load) { Text("Retry") }
			}

		is MobileUiState.Success -> ModuleContent(
			module = s.module,
			screenData = viewModel.screenData.collectAsStateWithLifecycle().value,
			onScreenSelected = viewModel::loadScreenData,
			onScreenTitleChange = onScreenTitleChange,
		)
	}
}

@Composable
private fun ModuleContent(
	module: ModuleUi,
	screenData: Map<String, List<Map<String, String>>>,
	onScreenSelected: (String) -> Unit,
	onScreenTitleChange: (String?) -> Unit = {},
) {
	// Default to the first nav item's screen, falling back to the first available screen.
	val defaultScreenKey = module.navigation.items.firstOrNull { module.screens.containsKey(it.screen) }?.screen
		?: module.screens.keys.firstOrNull()
	var selectedScreen by remember(module.moduleId) { mutableStateOf(defaultScreenKey) }

	// Fetch rows whenever the selected list screen changes (cached in the ViewModel).
	LaunchedEffect(selectedScreen) {
		val key = selectedScreen ?: return@LaunchedEffect
		if (module.screens[key] is ListScreenSpec) onScreenSelected(key)
	}

	// Report the human-readable label of the current screen so the scaffold can
	// render a deeper breadcrumb trail (Dashboard > Module > Screen).
	LaunchedEffect(selectedScreen, module.moduleId) {
		val label = module.navigation.items.firstOrNull { it.screen == selectedScreen }?.label
		onScreenTitleChange(label)
	}

	Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
		Column(Modifier.fillMaxWidth().padding(16.dp)) {
			Text(module.moduleName, style = MaterialTheme.typography.headlineMedium)
			Text("v${module.version}", style = MaterialTheme.typography.labelMedium)
		}

		Row(
			Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()).padding(horizontal = 16.dp),
			horizontalArrangement = Arrangement.spacedBy(8.dp),
		) {
			module.navigation.items.forEach { item ->
				val enabled = module.screens.containsKey(item.screen)
				FilterChip(
					selected = selectedScreen == item.screen,
					enabled = enabled,
					onClick = { selectedScreen = item.screen },
					label = { Text(item.label) },
					leadingIcon = { Icon(iconFor(item.icon), contentDescription = null) },
				)
			}
		}

		HorizontalDivider(Modifier.padding(vertical = 8.dp))

		when (val screen = selectedScreen?.let { module.screens[it] }) {
			is ListScreenSpec -> DynamicListScreen(
				spec = screen,
				rows = screenData[selectedScreen].orEmpty(),
				onAction = { action ->
					// If the action targets another screen in this module, navigate to it
					// in-place so View/Edit surface the detail/form contract screen.
					val target = action.navigateTo
						?.substringAfterLast('/')
						?.takeIf { module.screens.containsKey(it) }
					if (target != null) selectedScreen = target
				},
			)
			is DetailScreenSpec -> DynamicDetailScreen(spec = screen)
			is FormScreenSpec -> DynamicFormScreen(spec = screen)
			is ChartScreenSpec -> ChartDashboard(
				charts = screen.charts,
				emptyMessage = screen.emptyStateMessage,
			)
			null -> Box(Modifier.fillMaxWidth().padding(24.dp), Alignment.Center) {
				Text("No screen available for this section.", style = MaterialTheme.typography.bodyMedium)
			}
		}
	}
}

/* ---------------- LIST ---------------- */

@Composable
fun DynamicListScreen(
	spec: ListScreenSpec,
	rows: List<Map<String, String>> = emptyList(),
	onAction: (ScreenAction) -> Unit = {},
) {
	var query by remember { mutableStateOf("") }
	Column(
		Modifier.fillMaxWidth().padding(16.dp),
		verticalArrangement = Arrangement.spacedBy(12.dp),
	) {
		Text(spec.title, style = MaterialTheme.typography.headlineSmall)

		if (spec.enableSearch) {
			OutlinedTextField(
				value = query,
				onValueChange = { query = it },
				leadingIcon = { Icon(Icons.Default.Search, null) },
				placeholder = { Text(spec.searchPlaceholder) },
				singleLine = true,
				modifier = Modifier.fillMaxWidth(),
			)
		}

		if (spec.enableFilter) spec.filters.forEach { DynamicFilter(it) }

		spec.actions.firstOrNull { it.action == ActionTypes.NAVIGATE && it.id == "add" }?.let { add ->
			Button(onClick = { onAction(add) }) {
				Icon(iconFor(add.icon), null); Spacer(Modifier.width(6.dp)); Text(add.label)
			}
		}

		// Any action that isn't the list-level "add" is treated as a per-row action.
		val rowActions = spec.actions.filter { it.id != "add" }

		// Confirmation dialog state shared by all rows (for destructive/confirmable actions).
		var pendingConfirm by remember { mutableStateOf<ScreenAction?>(null) }
		pendingConfirm?.let { action ->
			AlertDialog(
				onDismissRequest = { pendingConfirm = null },
				title = { Text(action.label) },
				text = { Text(action.confirmationMessage ?: "Are you sure?") },
				confirmButton = {
					TextButton(onClick = { onAction(action); pendingConfirm = null }) { Text(action.label) }
				},
				dismissButton = {
					TextButton(onClick = { pendingConfirm = null }) { Text("Cancel") }
				},
			)
		}

		val onRowAction: (ScreenAction) -> Unit = { action ->
			if (action.requiresConfirmation) pendingConfirm = action else onAction(action)
		}

		if (rows.isEmpty()) {
			Text(spec.emptyStateMessage, style = MaterialTheme.typography.bodyMedium)
		} else if (shouldUseTable(spec.columns)) {
			ListAsTable(spec.columns, rows, rowActions, onRowAction)
		} else {
			ListAsCards(spec.columns, rows, rowActions, onRowAction)
		}
	}
}

/** Per-row overflow (â‹®) menu with an icon on every item. Destructive items are tinted. */
@Composable
private fun RowActionsMenu(
	actions: List<ScreenAction>,
	onAction: (ScreenAction) -> Unit,
	modifier: Modifier = Modifier,
) {
	if (actions.isEmpty()) return
	var expanded by remember { mutableStateOf(false) }
	Box(modifier) {
		IconButton(onClick = { expanded = true }) {
			Icon(Icons.Default.MoreVert, contentDescription = "Actions")
		}
		DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
			actions.forEach { action ->
				val destructive = action.id == "delete" || action.id == "reject"
				val tint = if (destructive) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
				DropdownMenuItem(
					text = { Text(action.label, color = tint) },
					leadingIcon = { Icon(iconFor(action.icon), contentDescription = null, tint = tint) },
					onClick = { expanded = false; onAction(action) },
				)
			}
		}
	}
}

/**
 * Heuristic: use a horizontal-scroll table when the data is dense (many columns or a wide
 * combined width); otherwise fall back to the mobile-native card layout.
 */
private fun shouldUseTable(columns: List<ListColumn>): Boolean {
	val totalWidth = columns.sumOf { it.width }
	return columns.size >= 5 || totalWidth > 560
}

/** Mobile-native card layout: emphasized title + label:value rows, badge columns become chips. */
@Composable
private fun ListAsCards(
	columns: List<ListColumn>,
	rows: List<Map<String, String>>,
	rowActions: List<ScreenAction> = emptyList(),
	onRowAction: (ScreenAction) -> Unit = {},
) {
	val titleCol = columns.firstOrNull()
	val badgeCol = columns.firstOrNull { it.type == FieldTypes.BADGE }
	val detailCols = columns.filter { it != titleCol && it != badgeCol }

	Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
		rows.forEach { row ->
			ElevatedCard(Modifier.fillMaxWidth()) {
				Column(
					Modifier.fillMaxWidth().padding(16.dp),
					verticalArrangement = Arrangement.spacedBy(8.dp),
				) {
					Row(
						Modifier.fillMaxWidth(),
						horizontalArrangement = Arrangement.SpaceBetween,
						verticalAlignment = Alignment.CenterVertically,
					) {
						Text(
							titleCol?.let { row[it.name].orEmpty() }.orEmpty().ifEmpty { "—" },
							style = MaterialTheme.typography.titleMedium,
							fontWeight = FontWeight.SemiBold,
							maxLines = 1,
							overflow = TextOverflow.Ellipsis,
							modifier = Modifier.weight(1f),
						)
						badgeCol?.let {
							Spacer(Modifier.width(8.dp))
							StatusChip(row[it.name].orEmpty())
						}
						RowActionsMenu(rowActions, { onRowAction(it.resolveFor(row)) })
					}
					detailCols.forEach { col ->
						Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
							Text(
								col.label,
								style = MaterialTheme.typography.labelMedium,
								color = MaterialTheme.colorScheme.onSurfaceVariant,
								modifier = Modifier.width(120.dp),
							)
							if (col.type == FieldTypes.BADGE) {
								StatusChip(row[col.name].orEmpty())
							} else {
								Text(
									row[col.name].orEmpty().ifEmpty { "—" },
									style = MaterialTheme.typography.bodyMedium,
									modifier = Modifier.weight(1f),
								)
							}
						}
					}
				}
			}
		}
	}
}

/** Dense table layout: honors each column's backend width, single-line + ellipsis, scrolls horizontally. */
@Composable
private fun ListAsTable(
	columns: List<ListColumn>,
	rows: List<Map<String, String>>,
	rowActions: List<ScreenAction> = emptyList(),
	onRowAction: (ScreenAction) -> Unit = {},
) {
	val hScroll = rememberScrollState()
	Column(Modifier.fillMaxWidth().horizontalScroll(hScroll)) {
		// Header
		Row(
			Modifier
				.background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
				.padding(vertical = 10.dp, horizontal = 4.dp),
		) {
			columns.forEach { col ->
				Text(
					col.label,
					style = MaterialTheme.typography.labelLarge,
					fontWeight = FontWeight.SemiBold,
					maxLines = 1,
					overflow = TextOverflow.Ellipsis,
					modifier = Modifier.width(col.width.dp).padding(horizontal = 8.dp),
				)
			}
			if (rowActions.isNotEmpty()) Spacer(Modifier.width(56.dp))
		}
		HorizontalDivider()
		// Rows
		rows.forEach { row ->
			Row(Modifier.padding(vertical = 10.dp, horizontal = 4.dp), verticalAlignment = Alignment.CenterVertically) {
				columns.forEach { col ->
					val cellMod = Modifier.width(col.width.dp).padding(horizontal = 8.dp)
					if (col.type == FieldTypes.BADGE) {
						StatusChip(row[col.name].orEmpty(), modifier = cellMod)
					} else {
						Text(
							row[col.name].orEmpty().ifEmpty { "—" },
							style = MaterialTheme.typography.bodyMedium,
							maxLines = 1,
							overflow = TextOverflow.Ellipsis,
							modifier = cellMod,
						)
					}
				}
				if (rowActions.isNotEmpty()) {
					RowActionsMenu(rowActions, { onRowAction(it.resolveFor(row)) }, Modifier.width(56.dp))
				}
			}
			HorizontalDivider()
		}
	}
}

/**
 * Resolves the row's id into any `{id}` placeholder in the action's navigateTo / apiEndpoint,
 * so per-row actions target the correct record.
 */
private fun ScreenAction.resolveFor(row: Map<String, String>): ScreenAction {
	val id = row["id"] ?: row["Id"] ?: return this
	return copy(
		navigateTo = navigateTo?.replace("{id}", id),
		apiEndpoint = apiEndpoint?.replace("{id}", id),
	)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DynamicFilter(filter: Filter) {
	var expanded by remember { mutableStateOf(false) }
	var selected by remember { mutableStateOf(filter.values.firstOrNull()?.label.orEmpty()) }
	ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }) {
		OutlinedTextField(
			value = selected,
			onValueChange = {},
			readOnly = true,
			label = { Text(filter.label) },
			trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
			modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable).fillMaxWidth(),
		)
		ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
			filter.values.forEach { v ->
				DropdownMenuItem(text = { Text(v.label) }, onClick = { selected = v.label; expanded = false })
			}
		}
	}
}

/* ---------------- DETAIL ---------------- */

@Composable
fun DynamicDetailScreen(
	spec: DetailScreenSpec,
	values: Map<String, String> = emptyMap(),
	onAction: (ScreenAction) -> Unit = {},
) {
	Column(
		Modifier.fillMaxWidth().padding(16.dp),
		verticalArrangement = Arrangement.spacedBy(16.dp),
	) {
		Text(spec.title, style = MaterialTheme.typography.headlineSmall)
		spec.sections.forEach { section ->
			ElevatedCard(Modifier.fillMaxWidth()) {
				Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
					Text(section.title, style = MaterialTheme.typography.titleMedium)
					section.fields.forEach { field ->
						Row(verticalAlignment = Alignment.CenterVertically) {
							field.icon?.let { Icon(iconFor(it), null); Spacer(Modifier.width(8.dp)) }
							Column {
								Text(field.label, style = MaterialTheme.typography.labelMedium)
								Text(
									values[field.name].orEmpty().ifEmpty { "â€”" },
									style = MaterialTheme.typography.bodyLarge,
								)
							}
						}
					}
				}
			}
		}
		Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
			spec.actions.forEach { action ->
				OutlinedButton(onClick = { onAction(action) }) {
					Icon(iconFor(action.icon), null); Spacer(Modifier.width(6.dp)); Text(action.label)
				}
			}
		}
	}
}

/* ---------------- FORM ---------------- */

@Composable
fun DynamicFormScreen(
	spec: FormScreenSpec,
	onSubmit: (ScreenAction, Map<String, String>) -> Unit = { _, _ -> },
	onCancel: (ScreenAction) -> Unit = {},
) {
	val values = remember { mutableStateMapOf<String, String>() }
	val errors = remember { mutableStateMapOf<String, String>() }

	Column(
		Modifier.fillMaxWidth().padding(16.dp),
		verticalArrangement = Arrangement.spacedBy(12.dp),
	) {
		Text(spec.title, style = MaterialTheme.typography.headlineSmall)

		spec.sections.forEach { section ->
			Text(section.title, style = MaterialTheme.typography.titleMedium)
			section.fields.forEach { field ->
				DynamicFormField(
					field = field,
					value = values[field.name].orEmpty(),
					error = errors[field.name],
					onValueChange = { values[field.name] = it; errors.remove(field.name) },
				)
			}
		}

		Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
			spec.actions.forEach { action ->
				when (action.action) {
					ActionTypes.API_CALL -> Button(onClick = {
						val found = validate(spec, values)
						errors.clear(); errors.putAll(found)
						if (found.isEmpty()) onSubmit(action, values.toMap())
					}) { Icon(iconFor(action.icon), null); Spacer(Modifier.width(6.dp)); Text(action.label) }

					else -> OutlinedButton(onClick = { onCancel(action) }) { Text(action.label) }
				}
			}
		}
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DynamicFormField(
	field: FormField,
	value: String,
	error: String?,
	onValueChange: (String) -> Unit,
) {
	val label = if (field.required) "${field.label} *" else field.label
	when (field.type) {
		FieldTypes.SELECT -> {
			var expanded by remember { mutableStateOf(false) }
			ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }) {
				OutlinedTextField(
					value = field.options.firstOrNull { it.value == value }?.label.orEmpty(),
					onValueChange = {},
					readOnly = true,
					label = { Text(label) },
					isError = error != null,
					supportingText = { error?.let { Text(it) } },
					trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
					modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable).fillMaxWidth(),
				)
				ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
					field.options.forEach { opt ->
						DropdownMenuItem(
							text = { Text(opt.label) },
							onClick = { onValueChange(opt.value); expanded = false },
						)
					}
				}
			}
		}
		else -> OutlinedTextField(
			value = value,
			onValueChange = onValueChange,
			label = { Text(label) },
			placeholder = { field.placeholder?.let { Text(it) } },
			supportingText = { (error ?: field.helpText)?.let { Text(it) } },
			isError = error != null,
			singleLine = true,
			keyboardOptions = KeyboardOptions(keyboardType = keyboardFor(field.type)),
			modifier = Modifier.fillMaxWidth(),
		)
	}
}

private fun keyboardFor(type: String): KeyboardType = when (type) {
	FieldTypes.EMAIL -> KeyboardType.Email
	FieldTypes.PHONE -> KeyboardType.Phone
	FieldTypes.NUMBER -> KeyboardType.Number
	else -> KeyboardType.Text
}

/** Drives validation entirely from the contract (required / length / regex + errorMessages fallback). */
private fun validate(spec: FormScreenSpec, values: Map<String, String>): Map<String, String> {
	val errors = mutableMapOf<String, String>()
	val messages = spec.validation.errorMessages
	spec.sections.flatMap { it.fields }.forEach { field ->
		val v = values[field.name].orEmpty().trim()
		if (field.required && v.isEmpty()) {
			errors[field.name] = field.validationMessage ?: messages["required"] ?: "This field is required"
			return@forEach
		}
		if (v.isEmpty()) return@forEach
		field.maxLength?.let { if (v.length > it) errors[field.name] = field.validationMessage ?: messages["maxLength"] ?: "Value is too long" }
		field.minLength?.let { if (v.length < it) errors[field.name] = field.validationMessage ?: "Value is too short" }
		field.pattern?.let { p ->
			if (!Regex(p).matches(v)) errors[field.name] = field.validationMessage ?: messages[field.type] ?: "Invalid value"
		}
	}
	return errors
}

/**
 * Maps the contract's Material icon names to Compose icons with full 1-to-1 parity to the
 * MudBlazor web app. Delegates to [MaterialIconResolver], which resolves the entire
 * material-icons-extended set by name (with a cached reflection lookup + fallback).
 */
fun iconFor(name: String): ImageVector = MaterialIconResolver.resolve(name)

/* ---------------- STATUS CHIPS ---------------- */

private enum class StatusTone { Positive, Warning, Negative, Neutral }

/** Maps a free-text status/badge value to a semantic tone (green / amber / red / neutral). */
private fun statusToneFor(value: String): StatusTone {
	val v = value.trim().lowercase()
	if (v.isEmpty()) return StatusTone.Neutral
	val positive = listOf(
		"active", "approved", "completed", "complete", "passed", "pass", "hired", "current",
		"valid", "enrolled", "on track", "ontrack", "paid", "open", "success", "won", "excellent",
		"good", "met", "achieved", "verified", "submitted", "confirmed", "yes",
	)
	val warning = listOf(
		"pending", "in progress", "in-progress", "inprogress", "review", "in review", "scheduled",
		"draft", "on hold", "onhold", "waiting", "expiring", "at risk", "atrisk", "partial",
		"probation", "onboarding", "interview", "offered", "maybe",
	)
	val negative = listOf(
		"inactive", "rejected", "failed", "fail", "expired", "overdue", "terminated", "cancelled",
		"canceled", "declined", "closed", "lost", "off track", "offtrack", "missed", "denied",
		"blocked", "suspended", "no",
	)
	return when {
		negative.any { v.contains(it) } -> StatusTone.Negative
		warning.any { v.contains(it) } -> StatusTone.Warning
		positive.any { v.contains(it) } -> StatusTone.Positive
		else -> StatusTone.Neutral
	}
}

@Composable
private fun StatusChip(value: String, modifier: Modifier = Modifier) {
	if (value.isBlank()) {
		Text("—", style = MaterialTheme.typography.bodyMedium, modifier = modifier)
		return
	}
	val scheme = MaterialTheme.colorScheme
	val (container, content) = when (statusToneFor(value)) {
		StatusTone.Positive -> Color(0xFF1B5E20).copy(alpha = 0.14f) to Color(0xFF1B5E20)
		StatusTone.Warning  -> Color(0xFFB26A00).copy(alpha = 0.16f) to Color(0xFFB26A00)
		StatusTone.Negative -> Color(0xFFB3261E).copy(alpha = 0.14f) to Color(0xFFB3261E)
		StatusTone.Neutral  -> scheme.surfaceVariant to scheme.onSurfaceVariant
	}
	Surface(
		color = container,
		contentColor = content,
		shape = MaterialTheme.shapes.small,
		modifier = modifier,
	) {
		Text(
			text = value,
			style = MaterialTheme.typography.labelMedium,
			fontWeight = FontWeight.SemiBold,
			maxLines = 1,
			overflow = TextOverflow.Ellipsis,
			modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
		)
	}
}
