package dev.logickoder.synote.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeleteNoteRequest(
    @SerialName("userID")
    val userId: String,
    @SerialName("noteID")
    val noteId: String,
)