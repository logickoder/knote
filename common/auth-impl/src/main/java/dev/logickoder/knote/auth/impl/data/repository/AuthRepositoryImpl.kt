package dev.logickoder.knote.auth.impl.data.repository

import com.google.firebase.auth.AuthCredential
import dev.logickoder.knote.auth.api.AuthRepository
import dev.logickoder.knote.auth.api.User
import dev.logickoder.knote.auth.impl.data.domain.DomainMapper
import dev.logickoder.knote.auth.impl.data.local.AuthDataStore
import dev.logickoder.knote.auth.impl.data.model.UserEntity
import dev.logickoder.knote.auth.impl.data.remote.AuthService
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
        get() = local.get().map { user ->
            user?.let {
                DomainMapper.toUser(it)
            }
        }

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