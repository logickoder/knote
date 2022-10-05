package dev.logickoder.knote.auth.api

import android.content.Context
import android.content.Intent
import dev.logickoder.knote.model.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUser: Flow<User?>

    suspend fun login(email: String, password: String): ResultWrapper<String>

    suspend fun loginWithGoogle(context: Context): Pair<Intent, suspend (Intent) -> Unit>

    suspend fun register(email: String, username: String, password: String): ResultWrapper<String>

    suspend fun logout()
}