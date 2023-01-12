package dev.logickoder.knote.notes.data.repository

import dev.logickoder.knote.model.ResultWrapper
import dev.logickoder.knote.notes.data.model.Note
import dev.logickoder.knote.notes.data.model.NoteAction
import dev.logickoder.knote.notes.data.model.NoteId
import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    val notes: Flow<List<Note>>

    suspend fun getNote(noteId: NoteId): Flow<Note>

    suspend fun sync(): ResultWrapper<Unit>

    suspend fun performAction(action: NoteAction?, vararg noteId: NoteId)

    suspend fun createNote(): Note

    suspend fun deleteNotes(vararg noteId: NoteId)

    suspend fun save(vararg note: Note): Array<NoteId>
}