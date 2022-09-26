package dev.logickoder.synote.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {

    suspend fun setDarkMode(data: Boolean) {
        context.local.edit { preferences ->
            preferences[DARK_MODE] = data
        }
    }

    fun getDarkMode(): Flow<Boolean> {
        return context.local.data.map { preferences ->
            preferences[DARK_MODE] ?: false
        }
    }

    companion object {
        private val Context.local by preferencesDataStore("settings")
        private val DARK_MODE = booleanPreferencesKey("dark_mode")
    }
}