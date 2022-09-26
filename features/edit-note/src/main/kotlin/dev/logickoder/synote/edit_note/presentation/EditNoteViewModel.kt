package dev.logickoder.synote.edit_note.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.logickoder.synote.notes.api.NoteId
import dev.logickoder.synote.notes.api.NotesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class EditNoteViewModel @Inject constructor(
    private val repository: NotesRepository,
) : ViewModel() {

    private val noteId = MutableStateFlow<NoteId?>(null)

    val note = combine(noteId, repository.notes) { id, notes ->
        notes.firstOrNull { it.id == id }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    fun getNote(id: NoteId?) {
        viewModelScope.launch {
            noteId.emit(id ?: repository.createNote())
        }
    }
}