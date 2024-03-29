package dev.logickoder.knote.auth.data.repository

import dev.logickoder.knote.auth.data.model.User
import dev.logickoder.knote.model.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUser: Flow<User?>

    suspend fun login(email: String, password: String): ResultWrapper<String>

    suspend fun signInWithCredential(credential: Any): ResultWrapper<String>

    suspend fun register(email: String, username: String, password: String): ResultWrapper<String>

    suspend fun logout()
}