package dev.logickoder.synote.data.repository

import dev.logickoder.synote.data.local.AuthDataStore
import dev.logickoder.synote.data.model.User
import dev.logickoder.synote.data.remote.api.AuthService
import dev.logickoder.synote.data.remote.dto.LoginRequest
import dev.logickoder.synote.data.remote.dto.LoginResponse
import dev.logickoder.synote.utils.ResultWrapper
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val remote: AuthService,
    private val local: AuthDataStore,
) {
    val currentUser: Flow<User?>
        get() = local.get()

    suspend fun login(username: String, password: String) = handleResponse(
        username,
        remote.login(LoginRequest(username, password))
    )

    suspend fun register(username: String, password: String) = handleResponse(
        username,
        remote.register(LoginRequest(username, password))
    )

    private suspend fun handleResponse(
        username: String,
        response: ResultWrapper<LoginResponse>
    ): ResultWrapper<String> {
        return if (response is ResultWrapper.Success) {
            if (!response.data.error) {
                val user = User(
                    id = response.data.userId!!,
                    name = username,
                )
                local.save(user)
                Napier.d("Saved $user to store")
                ResultWrapper.Success(response.data.message)
            } else ResultWrapper.Failure(response.data.message)
        } else response as ResultWrapper.Failure
    }
}