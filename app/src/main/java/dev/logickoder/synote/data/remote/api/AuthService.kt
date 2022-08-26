package dev.logickoder.synote.data.remote.api

import dev.logickoder.synote.data.remote.HttpRoutes
import dev.logickoder.synote.data.remote.dto.LoginRequest
import dev.logickoder.synote.data.remote.dto.LoginResponse
import dev.logickoder.synote.utils.ResultWrapper
import dev.logickoder.synote.utils.call
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import javax.inject.Inject

class AuthService @Inject constructor(
    private val client: HttpClient
) {
    suspend fun login(request: LoginRequest): ResultWrapper<LoginResponse> {
        return client.call {
            post(HttpRoutes.LOGIN) {
                setBody(request)
            }.body()
        }
    }

    suspend fun register(request: LoginRequest): ResultWrapper<LoginResponse> {
        return client.call {
            post(HttpRoutes.REGISTER) {
                setBody(request)
            }.body()
        }
    }
}