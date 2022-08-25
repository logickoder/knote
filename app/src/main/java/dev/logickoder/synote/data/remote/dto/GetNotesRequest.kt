package dev.logickoder.synote.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetNotesRequest(
    @SerialName("userID")
    val userId: String,
)