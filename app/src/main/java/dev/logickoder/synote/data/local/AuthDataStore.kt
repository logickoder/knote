package dev.logickoder.synote.data.local

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import dev.logickoder.synote.data.model.UserEntity
import dev.logickoder.synote.di.LocalStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class AuthDataStore @Inject constructor(private val store: LocalStore) {

    suspend fun save(data: UserEntity) {
        store.edit { preferences ->
            preferences[USER] = Json.encodeToString(data)
        }
    }

    fun get(): Flow<UserEntity?> {
        return store.data.map { preferences ->
            preferences[USER]?.let {
                Json.decodeFromString<UserEntity>(it)
            }
        }
    }

    companion object {
        private val USER = stringPreferencesKey("user")
    }
}