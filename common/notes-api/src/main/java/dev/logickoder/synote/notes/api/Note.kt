package dev.logickoder.synote.notes.api

import java.time.LocalDateTime

data class Note(
    val id: NoteId,
    val title: String,
    val content: String,
    val dateCreated: LocalDateTime,
    val dateModified: LocalDateTime? = null,
)