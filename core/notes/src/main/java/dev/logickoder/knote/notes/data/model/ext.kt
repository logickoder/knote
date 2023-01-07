package dev.logickoder.knote.notes.data.model

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