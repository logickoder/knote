package dev.logickoder.synote.data.repository

import dev.logickoder.synote.data.local.SettingsDataStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingsRepository @Inject constructor(
    private val local: SettingsDataStore,
) {
    val darkMode: Flow<Boolean>
        get() = local.getDarkMode()

    suspend fun setDarkMode(data: Boolean) = local.setDarkMode(data)
}