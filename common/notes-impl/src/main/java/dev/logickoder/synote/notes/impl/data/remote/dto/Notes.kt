package dev.logickoder.synote.notes.impl.data.remote.dto

import dev.logickoder.synote.notes.impl.data.model.NoteEntity

internal data class Notes(
    val notes: List<NoteEntity>,
)