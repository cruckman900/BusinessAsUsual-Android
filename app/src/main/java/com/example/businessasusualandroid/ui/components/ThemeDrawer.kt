package com.example.businessasusualandroid.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ThemeDrawer(
    drawerState: DrawerState,
    currentTheme: String,
    darkTheme: Boolean,
    onThemeSelected: (String) -> Unit,
    onDarkModeToggle: (Boolean) -> Unit
) {
    ModalDrawerSheet(
        drawerContainerColor = MaterialTheme.colorScheme.surface,
        drawerContentColor = MaterialTheme.colorScheme.onSurface
    ) {
        Text(
            "Themes",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )

        // Light/Dark toggle
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Dark Mode", modifier = Modifier.weight(1f))
            Switch(checked = darkTheme, onCheckedChange = onDarkModeToggle)
        }

        Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))

        // Theme list
        listOf(
            "bau",
            "hazard",
            "midnight",
            "armada",
            "ocean",
            "steel",
            "sunset",
            "emerald",
            "purple",
            "brownstone"
        ).forEach { theme ->
            val isSelected = currentTheme == theme
            NavigationDrawerItem(
                label = {
                    Text(
                        text = theme.replaceFirstChar { it.uppercase() },
                        color = if (isSelected)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.onSurface
                    )
                },
                selected = currentTheme == theme,
                onClick = { onThemeSelected(theme) }
            )
        }
    }
}