package dev.logickoder.synote.notes.api

import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    val notes: Flow<List<Note>>

    suspend fun getNote(noteId: NoteId): Flow<Note>

    suspend fun refreshNotes()

    suspend fun performAction(action: NoteAction?, vararg noteId: NoteId)

    suspend fun createNote(): Note

    suspend fun deleteNotes(vararg noteId: NoteId)

    suspend fun save(vararg note: Note): Array<NoteId>
}