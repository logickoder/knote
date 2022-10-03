package dev.logickoder.synote.settings.data.local

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

internal class SettingsDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {

    suspend inline fun <reified T> save(key: Preferences.Key<String>, data: T) {
        context.local.edit { preferences ->
            preferences[key] = Json.encodeToString(data)
        }
    }

    inline fun <reified T> get(key: Preferences.Key<String>): Flow<T?> {
        return context.local.data.map { preferences ->
            preferences[key]?.let {
                Json.decodeFromString<T>(it)
            }
        }
    }

    companion object {
        private val Context.local by preferencesDataStore(name = "settings")
    }
}