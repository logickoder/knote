package dev.logickoder.knote.auth.data.repository

import com.google.firebase.auth.AuthCredential
import dev.logickoder.knote.auth.data.local.AuthDataStore
import dev.logickoder.knote.auth.data.model.User
import dev.logickoder.knote.auth.data.model.UserEntity
import dev.logickoder.knote.auth.data.model.toUser
import dev.logickoder.knote.auth.data.remote.AuthService
import dev.logickoder.knote.model.ResultWrapper
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class AuthRepositoryImpl @Inject constructor(
    private val remote: AuthService,
    private val local: AuthDataStore,
) : AuthRepository {
    override val currentUser: Flow<User?>
        get() = local.get().map { it?.toUser() }

    override suspend fun login(email: String, password: String) = handleResponse(
        remote.login(email, password)
    )

    override suspend fun register(
        email: String,
        username: String,
        password: String
    ) = handleResponse(
        remote.register(email, password, username)
    )

    override suspend fun signInWithCredential(credential: Any): ResultWrapper<String> {
        return when (credential) {
            is AuthCredential -> handleResponse(remote.loginWithCredential(credential))
            else -> throw UnsupportedOperationException("Only Firebase AuthCredentials are supported")
        }
    }

    override suspend fun logout() {
        remote.logout()
        local.clear()
    }

    private suspend fun handleResponse(
        response: ResultWrapper<UserEntity>
    ): ResultWrapper<String> {
        return if (response is ResultWrapper.Success) {
            local.save(response.data)
            Napier.d("Saved ${response.data} to store")
            ResultWrapper.Success("Log in successful")
        } else response as ResultWrapper.Failure
    }
}