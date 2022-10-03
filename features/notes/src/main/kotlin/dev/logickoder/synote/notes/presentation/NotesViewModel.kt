package dev.logickoder.synote.notes.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.logickoder.synote.notes.api.Note
import dev.logickoder.synote.notes.api.NoteAction
import dev.logickoder.synote.notes.api.NoteId
import dev.logickoder.synote.notes.api.NotesRepository
import dev.logickoder.synote.notes.data.domain.NoteDomain
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class NotesViewModel @Inject constructor(
    private val repository: NotesRepository,
) : ViewModel() {

    private val _search = MutableStateFlow("")
    val search = _search.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = _search.value,
    )

    private val _selected = MutableStateFlow(listOf<NoteId>())
    val selected = _selected.map {
        it.size
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = _selected.value.size,
    )

    private val _screen = MutableStateFlow(NotesDrawerItem.Notes)
    val screen = _screen.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = _screen.value,
    )

    val notes = combine(
        repository.notes,
        _search,
        _selected,
        _screen,
        transform = { notes, filter, selected, screen ->
            notes.screen(screen).search(filter).map(selected)
        }
    ).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), persistentListOf())

    fun performAction(action: NoteAction?) {
        viewModelScope.launch {
            val notes = _selected.value.toTypedArray()
            // completely delete the note if the action is delete in the delete screen
            if (action == NoteAction.Trash && _screen.value == NotesDrawerItem.Trash) {
                repository.deleteNotes(*notes)
            } else {
                // else put the note in the trash
                repository.performAction(action, *notes)
            }
            cancelSelection()
        }
    }

    fun search(text: String) {
        viewModelScope.launch {
            _search.emit(text)
        }
    }

    fun toggleSelect(id: NoteId) {
        viewModelScope.launch {
            if (id in _selected.value) {
                _selected.emit(_selected.value - id)
            } else _selected.emit(_selected.value + id)
        }
    }

    fun changeScreen(screen: NotesDrawerItem) {
        if (screen != _screen.value) {
            viewModelScope.launch {
                _screen.emit(screen)
            }
        }
    }

    fun cancelSelection() {
        viewModelScope.launch {
            _selected.emit(emptyList())
        }
    }

    private fun List<Note>.search(text: String): List<Note> {
        val result = if (text.isNotBlank()) filter {
            it.title.contains(text, ignoreCase = true)
                    || it.content.contains(text, ignoreCase = true)
        } else this
        return result.toImmutableList()
    }

    private fun List<Note>.screen(screen: NotesDrawerItem): List<Note> {
        return filter {
            it.action == when (screen) {
                NotesDrawerItem.Archive -> NoteAction.Archive
                NotesDrawerItem.Trash -> NoteAction.Trash
                else -> null
            }
        }
    }

    private fun List<Note>.map(selected: List<NoteId>): ImmutableList<NoteDomain> {
        return map {
            NoteDomain(
                note = it,
                selected = it.id in selected
            )
        }.toImmutableList()
    }
}