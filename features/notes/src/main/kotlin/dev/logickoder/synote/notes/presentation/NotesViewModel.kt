package dev.logickoder.synote.notes.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.logickoder.synote.notes.api.Note
import dev.logickoder.synote.notes.api.NoteId
import dev.logickoder.synote.notes.api.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class NotesViewModel @Inject constructor(
    private val repository: NotesRepository,
) : ViewModel() {

    private val filter = MutableStateFlow("")

    val notes = combine(
        repository.notes,
        filter,
        transform = { notes, filter -> notes.filter(filter) }
    ).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun deleteNote(id: NoteId) {
        viewModelScope.launch {
            repository.deleteNote(id)
        }
    }

    fun search(text: String) {
        viewModelScope.launch(Dispatchers.Main) {
            filter.emit(text)
        }
    }

    private fun List<Note>.filter(text: String): List<Note> {
        return if (text.isNotBlank()) filter {
            it.title.contains(text, ignoreCase = true)
                    || it.content.contains(text, ignoreCase = true)
        } else this
    }
}