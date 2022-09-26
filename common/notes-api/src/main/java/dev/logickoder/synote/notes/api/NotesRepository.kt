package dev.logickoder.synote.notes.api

import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    val notes: Flow<List<Note>>

    suspend fun getNote(noteId: NoteId): Flow<Note>

    suspend fun refreshNotes()

    suspend fun deleteNote(noteId: NoteId)

    suspend fun createNote(): NoteId
}