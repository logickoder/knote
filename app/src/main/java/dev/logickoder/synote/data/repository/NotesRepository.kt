package dev.logickoder.synote.data.repository

import dev.logickoder.synote.data.local.NotesDao
import dev.logickoder.synote.data.model.NoteEntity
import dev.logickoder.synote.data.remote.api.NotesService
import dev.logickoder.synote.data.remote.dto.GetNotesRequest
import dev.logickoder.synote.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow
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
            local.save(*result.data.data?.toTypedArray()!!)
        }
    }
}