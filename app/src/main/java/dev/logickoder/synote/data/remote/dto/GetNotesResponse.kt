package dev.logickoder.synote.data.remote.dto

import dev.logickoder.synote.data.model.NoteEntity
import kotlinx.serialization.Serializable

@Serializable
data class GetNotesResponse(
    val error: Boolean,
    val message: String,
    val data: List<NoteEntity>? = null,
)