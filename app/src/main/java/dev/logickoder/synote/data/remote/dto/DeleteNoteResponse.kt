package dev.logickoder.synote.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class DeleteNoteResponse(
    val error: Boolean,
    val message: String,
)