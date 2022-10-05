package dev.logickoder.knote.auth.impl.data.repository

import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.GoogleAuthProvider
import dev.logickoder.knote.auth.api.AuthRepository
import dev.logickoder.knote.auth.api.User
import dev.logickoder.knote.auth.impl.data.domain.DomainMapper
import dev.logickoder.knote.auth.impl.data.local.AuthDataStore
import dev.logickoder.knote.auth.impl.data.model.UserEntity
import dev.logickoder.knote.auth.impl.data.remote.AuthService
import dev.logickoder.knote.auth_impl.R
import dev.logickoder.knote.model.ResultWrapper
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

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

    override suspend fun logout() = local.clear()


    private suspend fun handleResponse(
        response: ResultWrapper<UserEntity>
    ): ResultWrapper<String> {
        return if (response is ResultWrapper.Success) {
            local.save(response.data)
            Napier.d("Saved ${response.data} to store")
            ResultWrapper.Success("Log in successful")
        } else response as ResultWrapper.Failure
    }

    override suspend fun loginWithGoogle(context: Context): Pair<Intent, suspend (Intent) -> Unit> {
        val signInIntent = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(context.getString(R.string.auth_impl_google_web_id))
            .requestId()
            .requestProfile()
            .build().let {
                GoogleSignIn.getClient(context, it)
            }.signInIntent
        return signInIntent to { signInWithGoogle(it) }
    }

    private suspend fun signInWithGoogle(intent: Intent): ResultWrapper<UserEntity> {
        return getCredentials(intent)?.let {
            remote.loginWithCredential(it)
        } ?: ResultWrapper.Failure("Couldn't get credentials")
    }

    private suspend fun getCredentials(intent: Intent) = suspendCoroutine { cont ->
        GoogleSignIn.getSignedInAccountFromIntent(intent).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                cont.resume(GoogleAuthProvider.getCredential(task.result.idToken, null))
            } else {
                cont.resume(null)
            }
        }
    }
}