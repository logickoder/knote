package dev.logickoder.synote.settings.data.repository

import androidx.datastore.preferences.core.stringPreferencesKey
import dev.logickoder.synote.settings.api.SettingsRepository
import dev.logickoder.synote.settings.api.SettingsToggle
import dev.logickoder.synote.settings.api.Theme
import dev.logickoder.synote.settings.data.local.SettingsDataStore
import dev.logickoder.synote.settings.data.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class SettingsRepositoryImpl @Inject constructor(
    private val local: SettingsDataStore,
) : SettingsRepository {

    override val theme: Flow<Theme>
        get() = local.get<ThemeEntity>(THEME).map {
            it.toTheme()
        }

    override val toggles: Flow<Map<SettingsToggle, Boolean>>
        get() = local.get<List<SettingsToggleEntity>>(TOGGLES).map {
            it.toSettingsToggle()
        }

    override suspend fun toggle(toggle: SettingsToggle) {
        val data = toggles.first().toMutableMap()
        data[toggle] = data[toggle]!!.not()
        local.save(TOGGLES, data.toEntity())
    }

    override suspend fun setTheme(theme: Theme) {
        local.save(THEME, theme.toEntity())
    }

    companion object {
        private val THEME = stringPreferencesKey("theme")
        private val TOGGLES = stringPreferencesKey("toggles")
    }
}