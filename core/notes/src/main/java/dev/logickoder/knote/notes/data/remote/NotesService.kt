package dev.logickoder.knote.notes.data.remote

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dev.logickoder.knote.auth.data.model.UserId
import dev.logickoder.knote.model.ResultWrapper
import dev.logickoder.knote.notes.data.model.NoteEntity
import dev.logickoder.knote.notes.data.model.toEntity
import dev.logickoder.knote.notes.data.remote.dto.NoteNetwork
import dev.logickoder.knote.notes.data.remote.dto.Notes
import io.github.aakira.napier.Napier
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

internal class NotesService @Inject constructor() {

    private val UserId.notesPath: DocumentReference
        get() = Firebase.firestore.collection("notes").document(id)

    suspend fun getNotes(
        userId: UserId
    ): ResultWrapper<List<NoteEntity>> = suspendCoroutine { cont ->
        userId.notesPath.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Napier.d("Retrieved notes successfully for user: $userId")
                val notes = task.result.toObject(Notes::class.java)?.notes ?: emptyList()
                cont.resume(ResultWrapper.Success(notes.map { it.toEntity() }))
            } else {
                Napier.e("Failed to get notes for user: $userId", task.exception ?: Throwable())
                cont.resume(ResultWrapper.Failure(task.exception ?: Throwable()))
            }
        }
    }

    suspend fun updateNotes(
        userId: UserId,
        notes: List<NoteNetwork>,
    ): ResultWrapper<Unit> = suspendCoroutine { cont ->
        userId.notesPath.set(Notes(notes)).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Napier.d("Updated notes successfully for user: $userId")
                cont.resume(ResultWrapper.Success(Unit))
            } else {
                Napier.e("Failed to update notes for user: $userId", task.exception ?: Throwable())
                cont.resume(ResultWrapper.Failure(task.exception ?: Throwable()))
            }
        }
    }
}