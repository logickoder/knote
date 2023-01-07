package dev.logickoder.knote.notes.data.remote.dto

import dev.logickoder.knote.notes.data.model.NoteEntity

internal data class Notes(
    val notes: List<NoteEntity>,
)