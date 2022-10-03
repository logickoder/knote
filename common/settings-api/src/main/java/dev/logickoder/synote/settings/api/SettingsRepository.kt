package dev.logickoder.synote.settings.api

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val theme: Flow<Theme>
    val toggles: Flow<Map<SettingsToggle, Boolean>>

    suspend fun toggle(toggle: SettingsToggle)
    suspend fun setTheme(theme: Theme)
}