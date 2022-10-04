package dev.logickoder.synote.settings.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.logickoder.synote.settings.api.SettingsToggle
import dev.logickoder.synote.settings.api.Theme
import dev.logickoder.synote.ui.theme.SynoteTheme
import dev.logickoder.synote.ui.theme.secondaryPadding
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap
import kotlin.random.Random

@Composable
internal fun SettingsScreen(
    theme: Theme,
    toggles: ImmutableMap<SettingsToggle, Boolean>,
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    themeChanged: (Theme) -> Unit,
    toggle: (SettingsToggle) -> Unit,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            SettingsAppBar(
                modifier = Modifier.fillMaxWidth(),
                navigateBack = navigateBack,
            )
        },
        content = { scaffoldPadding ->
            LazyColumn(
                modifier = Modifier.padding(scaffoldPadding),
                contentPadding = PaddingValues(secondaryPadding()),
                content = {
                    item {
                        SettingsThemeItem(
                            modifier = Modifier.fillMaxWidth(),
                            theme = theme,
                            onThemeChanged = themeChanged,
                        )
                        Spacer(modifier = Modifier.height(secondaryPadding()))
                    }
                    toggles.entries.forEach { item ->
                        item {
                            SettingsToggleItem(
                                modifier = Modifier.fillMaxWidth(),
                                toggle = item.key,
                                checked = item.value,
                                onToggle = {
                                    toggle(item.key)
                                },
                            )
                            Spacer(modifier = Modifier.height(secondaryPadding()))
                        }
                    }
                }
            )
        }
    )
}

@Preview
@Composable
private fun SettingsScreenPreview() = SynoteTheme {
    SettingsScreen(
        theme = Theme.System,
        toggles = buildMap {
            SettingsToggle.values().forEach {
                put(it, Random(Integer.MAX_VALUE).nextBoolean())
            }
        }.toImmutableMap(),
        navigateBack = {},
        themeChanged = {},
        toggle = {},
    )
}