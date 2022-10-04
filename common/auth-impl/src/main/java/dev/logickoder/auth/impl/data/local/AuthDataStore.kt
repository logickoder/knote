package dev.logickoder.auth.impl.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.logickoder.auth.impl.data.model.UserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

internal class AuthDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {

    suspend fun save(user: UserEntity) {
        context.local.edit { preferences ->
            preferences[USER] = Json.encodeToString(user)
        }
    }

    fun get(): Flow<UserEntity?> {
        return context.local.data.map { preferences ->
            preferences[USER]?.let {
                Json.decodeFromString<UserEntity>(it)
            }
        }
    }

    companion object {
        private val USER = stringPreferencesKey("user")
        private val Context.local by preferencesDataStore(name = "auth")
    }
}