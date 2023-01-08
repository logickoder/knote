package dev.logickoder.knote.settings.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import dev.logickoder.knote.settings.R
import dev.logickoder.knote.settings.data.model.SettingsToggle
import dev.logickoder.knote.settings.data.model.Theme
import dev.logickoder.knote.ui.theme.KNoteTheme

@Composable
internal fun SettingsToggleItem(
    toggle: SettingsToggle,
    checked: Boolean,
    modifier: Modifier = Modifier,
    onToggle: () -> Unit,
) {
    SettingsItem(
        modifier = modifier,
        title = toggle.text,
        trailing = {
            Switch(
                checked = checked,
                onCheckedChange = {
                    onToggle()
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colors.primary,
                    checkedTrackColor = MaterialTheme.colors.primary,
                ),
            )
        },
        onClick = onToggle
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun SettingsThemeItem(
    theme: Theme,
    modifier: Modifier = Modifier,
    onThemeChanged: (Theme) -> Unit,
) {
    var showDialog by remember { mutableStateOf(false) }
    SettingsItem(
        title = "Theme",
        modifier = modifier,
        trailing = {
            Text(theme.name)
        },
        onClick = {
            showDialog = true
        }
    )

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text(
                    text = stringResource(R.string.settings_choose_theme),
                    style = MaterialTheme.typography.h5
                )
            },
            shape = MaterialTheme.shapes.large,
            text = {
                Column {
                    Theme.values().forEach {
                        ListItem(
                            modifier = Modifier.clickable {
                                onThemeChanged(it)
                                showDialog = false
                            },
                            icon = {
                                RadioButton(
                                    selected = it == theme,
                                    onClick = null,
                                )
                            },
                            text = {
                                Text(it.name)
                            },
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = { showDialog = false },
                    content = {
                        Text(stringResource(R.string.settings_cancel))
                    }
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SettingsItem(
    modifier: Modifier = Modifier,
    title: String,
    trailing: @Composable () -> Unit,
    onClick: () -> Unit,
) = ListItem(
    modifier = modifier.clickable { onClick() },
    singleLineSecondaryText = true,
    text = {
        Text(
            text = title,
            style = LocalTextStyle.current.copy(
                fontWeight = FontWeight.W500,
            ),
            maxLines = 1,
        )
    },
    trailing = trailing,
)


@Preview
@Composable
private fun SettingsThemeItemPreview() = KNoteTheme {
    SettingsThemeItem(theme = Theme.System, onThemeChanged = {})
}

@Preview
@Composable
private fun SettingsToggleItemCheckedPreview() = KNoteTheme {
    SettingsToggleItem(
        toggle = SettingsToggle.AddNewNotesToBottom,
        checked = true,
        onToggle = {},
    )
}

@Preview
@Composable
private fun SettingsToggleItemUncheckedPreview() = KNoteTheme {
    SettingsToggleItem(
        toggle = SettingsToggle.AddNewNotesToBottom,
        checked = false,
        onToggle = {},
    )
}