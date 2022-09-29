package dev.logickoder.synote.notes.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.logickoder.synote.notes.api.Note
import dev.logickoder.synote.notes.api.NoteId
import dev.logickoder.synote.notes.api.NotesRepository
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class NotesViewModel @Inject constructor(
    private val repository: NotesRepository,
) : ViewModel() {

    private val _search = MutableStateFlow("")
    val search = _search.asStateFlow()

    val notes = combine(
        repository.notes,
        _search,
        transform = { notes, filter -> notes.filter(filter) }
    ).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), persistentListOf())

    fun deleteNote(id: NoteId) {
        viewModelScope.launch {
            repository.deleteNote(id)
        }
    }

    fun search(text: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _search.emit(text)
        }
    }

    private fun List<Note>.filter(text: String): ImmutableList<Note> {
        val result = if (text.isNotBlank()) filter {
            it.title.contains(text, ignoreCase = true)
                    || it.content.contains(text, ignoreCase = true)
        } else this
        return result.toImmutableList()
    }
}