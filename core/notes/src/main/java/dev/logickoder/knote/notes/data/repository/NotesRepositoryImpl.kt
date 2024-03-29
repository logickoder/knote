package dev.logickoder.knote.notes.data.repository

import dev.logickoder.knote.model.ResultWrapper
import dev.logickoder.knote.notes.data.local.NotesDao
import dev.logickoder.knote.notes.data.model.Note
import dev.logickoder.knote.notes.data.model.NoteAction
import dev.logickoder.knote.notes.data.model.NoteEntity
import dev.logickoder.knote.notes.data.model.NoteId
import dev.logickoder.knote.notes.data.model.toEntity
import dev.logickoder.knote.notes.data.model.toNetwork
import dev.logickoder.knote.notes.data.model.toNote
import dev.logickoder.knote.notes.data.remote.NotesService
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
    private val authRepository: dev.logickoder.knote.auth.data.repository.AuthRepository,
) : NotesRepository {
    override val notes: Flow<List<Note>>
        get() = local.getNotes().map { notes ->
            notes.map { it.toNote() }
        }

    override suspend fun getNote(noteId: NoteId): Flow<Note> = local.getNote(noteId).map {
        it.toNote()
    }

    override suspend fun sync(): ResultWrapper<Unit> {
        val userId = authRepository.currentUser.first()?.id ?: return ResultWrapper.Failure(
            Throwable()
        )
        val notes = notes.first()
        return if (notes.isEmpty()) {
            // fetch the notes from the server and save it in the device
            when (val result = remote.getNotes(userId)) {
                is ResultWrapper.Success -> {
                    // save the new notes from the server
                    local.save(*result.data.toTypedArray())
                    ResultWrapper.Success(Unit)
                }

                is ResultWrapper.Failure -> ResultWrapper.Failure(result.error)
            }
        } else {
            // update the notes in the server with the local notes
            remote.updateNotes(userId, notes.map { it.toNetwork() })
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