package dev.logickoder.knote.notes.data.model

import dev.logickoder.knote.notes.data.remote.dto.NoteNetwork
import java.time.LocalDateTime
import java.time.ZoneOffset

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

internal fun Note.toNetwork() = NoteNetwork(
    id = id.id,
    title = title,
    content = content,
    dateCreated = dateCreated.toEpochSecond(ZoneOffset.UTC),
    dateModified = dateModified.toEpochSecond(ZoneOffset.UTC),
    action = action,
)

internal fun NoteNetwork.toEntity() = NoteEntity(
    id = NoteId(id),
    title = title,
    content = content,
    dateCreated = LocalDateTime.ofEpochSecond(dateCreated, 0, ZoneOffset.UTC),
    dateModified = LocalDateTime.ofEpochSecond(dateModified, 0, ZoneOffset.UTC),
    action = action,
)