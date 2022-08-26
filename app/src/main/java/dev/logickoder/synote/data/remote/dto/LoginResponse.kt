package dev.logickoder.synote.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val error: Boolean,
    val message: String,
    @SerialName("userID")
    val userId: String? = null,
)
