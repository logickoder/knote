package dev.logickoder.knote.notes.impl.data.remote.dto

import dev.logickoder.knote.notes.impl.data.model.NoteEntity

internal data class Notes(
    val notes: List<NoteEntity>,
)