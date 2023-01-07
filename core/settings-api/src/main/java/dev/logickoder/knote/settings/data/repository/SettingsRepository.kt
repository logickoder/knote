package dev.logickoder.knote.settings.data.repository

import dev.logickoder.knote.settings.api.Theme
import dev.logickoder.knote.settings.presentation.model.SettingsToggle
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val theme: Flow<Theme>
    val toggles: Flow<Map<SettingsToggle, Boolean>>

    suspend fun toggle(toggle: SettingsToggle)
    suspend fun setTheme(theme: Theme)
}