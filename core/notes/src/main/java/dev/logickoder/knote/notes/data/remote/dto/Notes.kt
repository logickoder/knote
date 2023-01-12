package dev.logickoder.knote.notes.data.remote.dto

internal data class Notes(
    val notes: List<NoteNetwork> = emptyList(),
)