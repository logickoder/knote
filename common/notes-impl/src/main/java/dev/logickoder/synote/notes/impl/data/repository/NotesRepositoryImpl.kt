package dev.logickoder.synote.notes.impl.data.repository

import dev.logickoder.synote.auth.api.AuthRepository
import dev.logickoder.synote.model.ResultWrapper
import dev.logickoder.synote.notes.api.Note
import dev.logickoder.synote.notes.api.NoteAction
import dev.logickoder.synote.notes.api.NoteId
import dev.logickoder.synote.notes.api.NotesRepository
import dev.logickoder.synote.notes.impl.data.domain.toEntity
import dev.logickoder.synote.notes.impl.data.domain.toNote
import dev.logickoder.synote.notes.impl.data.local.NotesDao
import dev.logickoder.synote.notes.impl.data.model.NoteEntity
import dev.logickoder.synote.notes.impl.data.remote.NotesService
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import javax.inject.Inject

internal class NotesRepositoryImpl @Inject constructor(
    private val remote: NotesService,
    private val local: NotesDao,
    private val authRepository: AuthRepository,
) : NotesRepository {
    override val notes: Flow<List<Note>>
        get() = local.getNotes().map { notes ->
            notes.map { it.toNote() }
        }

    override suspend fun getNote(noteId: NoteId): Flow<Note> = local.getNote(noteId).map {
        it.toNote()
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

    override suspend fun performAction(
        action: NoteAction?,
        vararg noteId: NoteId
    ) {
        val notes = notes.map { notes ->
            notes.filter {
                it.id in noteId
            }.map { note ->
                note.copy(action = action).toEntity()
            }
        }.first()
        local.save(*notes.toTypedArray())
    }

    override suspend fun deleteNotes(vararg noteId: NoteId) {
        val notes = local.getNotes().first()
        // remove the note from the local storage
        local.delete(*notes.filter { it.id in noteId }.toTypedArray())
    }

    override suspend fun createNote() = NoteEntity().toNote()

    override suspend fun save(vararg note: Note): Array<NoteId> = coroutineScope {
        val filter: (Note) -> Boolean = {
            it.content.isBlank() && it.title.isBlank()
        }
        // save notes with content
        val notesToSave = note.filterNot(filter).map {
            it.toEntity().copy(dateModified = LocalDateTime.now())
        }
        // remove notes without content
        val notesToRemove = note.filter(filter).map { it.toEntity() }
        val remove = async { local.delete(*notesToRemove.toTypedArray()) }
        val saved = local.save(*notesToSave.toTypedArray())
        remove.await()
        return@coroutineScope saved.map { NoteId(it) }.toTypedArray()
    }
}