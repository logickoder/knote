package dev.logickoder.synote.data.repository

import dev.logickoder.synote.data.local.NotesDao
import dev.logickoder.synote.data.model.NoteEntity
import dev.logickoder.synote.data.remote.api.NotesService
import dev.logickoder.synote.data.remote.dto.DeleteNoteRequest
import dev.logickoder.synote.data.remote.dto.GetNotesRequest
import dev.logickoder.synote.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class NotesRepository @Inject constructor(
    private val remote: NotesService,
    private val local: NotesDao,
) {
    val notes: Flow<List<NoteEntity>>
        get() = local.getNotes()

    suspend fun refreshNotes(userId: String) {
        val result = remote.getNotes(GetNotesRequest(userId))
        if (result is ResultWrapper.Success && !result.data.error) {
            val newNotes = result.data.data!!
            // filter out notes not found in the new notes
            val notesToRemove = local.getNotes().first().filterNot { it in newNotes }
            // remove the previous notes not found in the server
            local.delete(*notesToRemove.toTypedArray())
            // save the new notes from the server
            local.save(*newNotes.toTypedArray())
        }
    }

    suspend fun deleteNote(userId: String, noteId: String) {
        // remove the note from the local storage
        local.delete(notes.first().first { it.id == noteId })
        // remove the note from the server
        val result = remote.deleteNote(DeleteNoteRequest(userId, noteId))
        // if the server delete was not successful,
        // refresh the notes from the server to get the deleted note back
        if ((result is ResultWrapper.Success && result.data.error.not()).not()) {
            refreshNotes(userId)
        }
    }
}