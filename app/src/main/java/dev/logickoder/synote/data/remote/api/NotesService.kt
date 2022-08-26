package dev.logickoder.synote.data.remote.api

import dev.logickoder.synote.data.remote.HttpRoutes
import dev.logickoder.synote.data.remote.dto.DeleteNoteRequest
import dev.logickoder.synote.data.remote.dto.DeleteNoteResponse
import dev.logickoder.synote.data.remote.dto.GetNotesRequest
import dev.logickoder.synote.data.remote.dto.GetNotesResponse
import dev.logickoder.synote.utils.ResultWrapper
import dev.logickoder.synote.utils.call
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import javax.inject.Inject

class NotesService @Inject constructor(
    private val client: HttpClient
) {
    suspend fun getNotes(request: GetNotesRequest): ResultWrapper<GetNotesResponse> {
        return client.call {
            post(HttpRoutes.GET_NOTES) {
                setBody(request)
            }.body()
        }
    }

    suspend fun deleteNote(request: DeleteNoteRequest): ResultWrapper<DeleteNoteResponse> {
        return client.call {
            post(HttpRoutes.DELETE_NOTE) {
                setBody(request)
            }.body()
        }
    }
}