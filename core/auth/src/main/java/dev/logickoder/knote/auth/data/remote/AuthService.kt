package dev.logickoder.knote.auth.data.remote

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dev.logickoder.knote.auth.data.domain.DomainMapper
import dev.logickoder.knote.auth.data.model.UserEntity
import dev.logickoder.knote.model.ResultWrapper
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

internal class AuthService @Inject constructor() {

    suspend fun login(
        email: String,
        password: String
    ): ResultWrapper<UserEntity> = suspendCoroutine { cont ->
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val result = auth.currentUser?.let {
                    ResultWrapper.Success(DomainMapper.toUserEntity(it))
                } ?: ResultWrapper.Failure("User not found")
                cont.resume(result)
            } else {
                cont.resume(ResultWrapper.Failure(task.exception ?: Throwable()))
            }
        }
    }

    suspend fun loginWithCredential(
        credential: AuthCredential,
    ): ResultWrapper<UserEntity> = suspendCoroutine { cont ->
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val result = auth.currentUser?.let {
                    ResultWrapper.Success(DomainMapper.toUserEntity(it))
                } ?: ResultWrapper.Failure("Invalid credential")
                cont.resume(result)
            } else {
                cont.resume(ResultWrapper.Failure(task.exception ?: Throwable()))
            }
        }
    }

    suspend fun register(
        email: String,
        password: String,
        username: String = "",
    ): ResultWrapper<UserEntity> = suspendCoroutine { cont ->
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val name = UserProfileChangeRequest.Builder().setDisplayName(username).build()
                auth.currentUser?.updateProfile(name)?.addOnCompleteListener { nameTask ->
                    if (nameTask.isSuccessful) {
                        val result = auth.currentUser?.let {
                            ResultWrapper.Success(DomainMapper.toUserEntity(it))
                        } ?: ResultWrapper.Failure("User not created")
                        cont.resume(result)
                    } else {
                        cont.resume(ResultWrapper.Failure(nameTask.exception ?: Throwable()))
                    }
                }
            } else {
                cont.resume(ResultWrapper.Failure(task.exception ?: Throwable()))
            }
        }
    }

    fun logout() = auth.signOut()

    companion object {
        private val auth = Firebase.auth
    }
}