package dev.logickoder.synote.notes.impl.data.domain

import dev.logickoder.synote.notes.api.Note
import dev.logickoder.synote.notes.impl.data.model.NoteEntity

internal fun NoteEntity.toNote() = Note(
    id = id,
    title = title,
    content = content,
    dateCreated = dateCreated,
    dateModified = dateModified,
    action = action,
)

internal fun Note.toEntity() = NoteEntity(
    id = id,
    title = title,
    content = content,
    dateCreated = dateCreated,
    dateModified = dateModified,
    action = action,
)