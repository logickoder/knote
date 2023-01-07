package dev.logickoder.knote.notes.data.model

import dev.logickoder.knote.notes.api.NoteAction
import java.time.LocalDateTime

data class Note(
    val id: NoteId,
    val title: String,
    val content: String,
    val dateCreated: LocalDateTime,
    val dateModified: LocalDateTime = dateCreated,
    val action: NoteAction?,
)