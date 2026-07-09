package com.businessasusual.app.ui.mobileui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.businessasusual.domain.model.*
import org.koin.androidx.compose.koinViewModel

@Composable
fun MobileUiScreen(viewModel: MobileUiViewModel = koinViewModel()) {
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

        is MobileUiState.Success -> ModuleContent(s.module)
    }
}

@Composable
private fun ModuleContent(module: ModuleUi) {
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
                AssistChip(
                    onClick = { /* TODO: navigate to item.screen */ },
                    label = { Text(item.label) },
                    leadingIcon = { Icon(iconFor(item.icon), contentDescription = null) },
                )
            }
        }

        HorizontalDivider(Modifier.padding(vertical = 8.dp))

        // Self-contained demo: build the Employee form entirely from the contract.
        module.formScreen?.let { DynamicFormScreen(spec = it) }

        // Ready to use once you feed real data:
        // module.listScreen?.let { DynamicListScreen(spec = it, rows = /* from HR data API */ emptyList()) }
        // module.detailScreen?.let { DynamicDetailScreen(spec = it, values = /* selected employee */ emptyMap()) }
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

        Row(Modifier.fillMaxWidth()) {
            spec.columns.forEach { col ->
                Text(col.label, style = MaterialTheme.typography.labelLarge, modifier = Modifier.weight(1f))
            }
        }
        HorizontalDivider()

        if (rows.isEmpty()) {
            Text(spec.emptyStateMessage, style = MaterialTheme.typography.bodyMedium)
        } else {
            rows.forEach { row ->
                Row(Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                    spec.columns.forEach { col ->
                        Text(row[col.name].orEmpty(), modifier = Modifier.weight(1f))
                    }
                }
                HorizontalDivider()
            }
        }
    }
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
            modifier = Modifier.menuAnchor().fillMaxWidth(),
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
                                    values[field.name].orEmpty().ifEmpty { "—" },
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
                    modifier = Modifier.menuAnchor().fillMaxWidth(),
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

/** Maps the contract's Material icon names to Compose icons (base-safe with a fallback). */
fun iconFor(name: String): ImageVector = when (name) {
    "add", "add_circle", "add_business", "person_add" -> Icons.Default.Add
    "edit" -> Icons.Default.Edit
    "email" -> Icons.Default.Email
    "phone" -> Icons.Default.Phone
    "people", "person", "person_search" -> Icons.Default.Person
    "home" -> Icons.Default.Home
    "settings" -> Icons.Default.Settings
    "search" -> Icons.Default.Search
    "save", "check" -> Icons.Default.Check
    "close", "cancel" -> Icons.Default.Close
    "delete", "block" -> Icons.Default.Delete
    "warning" -> Icons.Default.Warning
    else -> Icons.Default.Info
}