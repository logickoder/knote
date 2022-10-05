package dev.logickoder.knote.settings.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SettingsRoute(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
) {
    val viewModel = viewModel<SettingsViewModel>()
    val theme by viewModel.theme.collectAsState()
    val toggles by viewModel.toggles.collectAsState()

    SettingsScreen(
        modifier = modifier,
        theme = theme,
        toggles = toggles,
        navigateBack = navigateBack,
        themeChanged = viewModel::updateTheme,
        toggle = viewModel::toggle
    )
}