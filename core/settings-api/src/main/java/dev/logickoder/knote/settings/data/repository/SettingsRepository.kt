package dev.logickoder.knote.settings.data.repository

import dev.logickoder.knote.settings.data.model.SettingsToggle
import dev.logickoder.knote.settings.data.model.Theme
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val theme: Flow<Theme>
    val toggles: Flow<Map<SettingsToggle, Boolean>>

    suspend fun toggle(toggle: SettingsToggle)
    suspend fun setTheme(theme: Theme)
}