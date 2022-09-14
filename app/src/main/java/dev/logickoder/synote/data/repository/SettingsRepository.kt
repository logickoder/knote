package dev.logickoder.synote.data.repository

import dev.logickoder.synote.data.local.SettingsDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class SettingsRepository @Inject constructor(
    private val local: SettingsDataStore,
) {
    val darkMode: Flow<Boolean>
        get() = local.getDarkMode()

    suspend fun toggleDarkMode() {
        local.setDarkMode(darkMode.first().not())
    }
}