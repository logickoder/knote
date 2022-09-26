package dev.logickoder.synote.auth.api

import dev.logickoder.synote.model.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUser: Flow<User?>

    suspend fun login(email: String, password: String): ResultWrapper<String>

    suspend fun register(email: String, username: String, password: String): ResultWrapper<String>
}