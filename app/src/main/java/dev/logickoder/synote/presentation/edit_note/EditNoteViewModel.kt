package dev.logickoder.synote.presentation.edit_note

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.logickoder.synote.data.model.NoteEntity
import dev.logickoder.synote.data.repository.NotesRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class EditNoteViewModel @Inject constructor(
    private val repository: NotesRepository,
) : ViewModel() {
    var note by mutableStateOf<NoteEntity?>(null)

    fun createNote(): NoteEntity {
        return note!!
    }

    suspend fun getNote(id: String) {
        note = repository.notes.first().first { it.id == id }
    }
}