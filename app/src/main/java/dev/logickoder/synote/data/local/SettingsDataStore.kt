package dev.logickoder.synote.data.local

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import dev.logickoder.synote.di.LocalStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsDataStore @Inject constructor(private val store: LocalStore) {

    suspend fun setDarkMode(data: Boolean) {
        store.edit { preferences ->
            preferences[DARK_MODE] = data
        }
    }

    fun getDarkMode(): Flow<Boolean> {
        return store.data.map { preferences ->
            preferences[DARK_MODE] ?: false
        }
    }

    companion object {
        private val DARK_MODE = booleanPreferencesKey("dark_mode")
    }
}