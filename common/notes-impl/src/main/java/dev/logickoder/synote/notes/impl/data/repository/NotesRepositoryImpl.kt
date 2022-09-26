package dev.logickoder.synote.notes.impl.data.repository

import dev.logickoder.synote.auth.api.AuthRepository
import dev.logickoder.synote.model.ResultWrapper
import dev.logickoder.synote.notes.api.Note
import dev.logickoder.synote.notes.api.NoteId
import dev.logickoder.synote.notes.api.NotesRepository
import dev.logickoder.synote.notes.impl.data.domain.DomainMapper
import dev.logickoder.synote.notes.impl.data.local.NotesDao
import dev.logickoder.synote.notes.impl.data.model.NoteEntity
import dev.logickoder.synote.notes.impl.data.remote.NotesService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class NotesRepositoryImpl @Inject constructor(
    private val remote: NotesService,
    private val local: NotesDao,
    private val authRepository: AuthRepository,
) : NotesRepository {
    override val notes: Flow<List<Note>>
        get() = local.getNotes().map { notes ->
            notes.map { DomainMapper.toNote(it) }
        }

    override suspend fun getNote(noteId: NoteId): Flow<Note> = local.getNote(noteId).map {
        DomainMapper.toNote(it)
    }

    override suspend fun refreshNotes() {
        val userId = authRepository.currentUser.first()?.id ?: return
        val result = remote.getNotes(userId)
        if (result is ResultWrapper.Success) {
            val newNotes = result.data
            // filter out notes not found in the new notes
            val notesToRemove = local.getNotes().first().filterNot { it in newNotes }
            // remove the previous notes not found in the server
            local.delete(*notesToRemove.toTypedArray())
            // save the new notes from the server
            local.save(*newNotes.toTypedArray())
        }
    }

    override suspend fun deleteNote(noteId: NoteId) {
        val notes = local.getNotes().first()
        // remove the note from the local storage
        local.delete(notes.first { it.id == noteId })
    }

    override suspend fun createNote(): NoteId {
        return NoteId(local.save(NoteEntity()).first())
    }
}