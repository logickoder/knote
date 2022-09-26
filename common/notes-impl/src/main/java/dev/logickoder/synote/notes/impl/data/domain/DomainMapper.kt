package dev.logickoder.synote.notes.impl.data.domain

import dev.logickoder.synote.notes.api.Note
import dev.logickoder.synote.notes.impl.data.model.NoteEntity

internal object DomainMapper {
    fun toNote(note: NoteEntity) = Note(
        id = note.id,
        title = note.title,
        content = note.content,
        dateCreated = note.dateCreated,
        dateModified = note.dateModified,
    )

    fun toNoteEntity(note: Note) = NoteEntity(
        id = note.id,
        title = note.title,
        content = note.content,
        dateCreated = note.dateCreated,
        dateModified = note.dateModified,
    )
}