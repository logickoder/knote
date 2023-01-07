package dev.logickoder.knote.notes.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.logickoder.knote.notes.api.NoteAction
import dev.logickoder.knote.notes.data.domain.NoteItem
import dev.logickoder.knote.notes.data.domain.NoteScreen
import dev.logickoder.knote.settings.presentation.model.SettingsToggle
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class NotesViewModel @Inject constructor(
    private val repository: dev.logickoder.knote.notes.data.repository.NotesRepository,
    settings: dev.logickoder.knote.settings.data.repository.SettingsRepository,
) : ViewModel() {

    private val _search = MutableStateFlow("")
    val search = _search.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = _search.value,
    )

    private val _selected = MutableStateFlow(listOf<dev.logickoder.knote.notes.data.model.NoteId>())
    val selected = _selected.map {
        it.size
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = _selected.value.size,
    )

    private val _screen = MutableStateFlow(NoteScreen.Notes)

    val notes = combine(
        repository.notes,
        _search,
        _selected,
        _screen,
        settings.toggles,
        transform = { notes, filter, selected, screen, settings ->
            notes.screen(screen).search(filter).sort(settings).map(selected)
        }
    ).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), persistentListOf())

    fun performAction(action: NoteAction?) {
        viewModelScope.launch {
            val notes = _selected.value.toTypedArray()
            // completely delete the note if the action is delete in the delete screen
            if (action == NoteAction.Trash && _screen.value == NoteScreen.Trash) {
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

    fun toggleSelect(id: dev.logickoder.knote.notes.data.model.NoteId) {
        viewModelScope.launch {
            if (id in _selected.value) {
                _selected.emit(_selected.value - id)
            } else _selected.emit(_selected.value + id)
        }
    }

    fun setScreen(screen: NoteScreen) {
        viewModelScope.launch {
            _screen.emit(screen)
        }
    }

    fun cancelSelection() {
        viewModelScope.launch {
            _selected.emit(emptyList())
        }
    }

    private fun List<dev.logickoder.knote.notes.data.model.Note>.search(text: String): List<dev.logickoder.knote.notes.data.model.Note> {
        val result = if (text.isNotBlank()) filter {
            it.title.contains(text, ignoreCase = true)
                    || it.content.contains(text, ignoreCase = true)
        } else this
        return result.toImmutableList()
    }

    private fun List<dev.logickoder.knote.notes.data.model.Note>.screen(screen: NoteScreen): List<dev.logickoder.knote.notes.data.model.Note> {
        return filter {
            it.action == when (screen) {
                NoteScreen.Archive -> NoteAction.Archive
                NoteScreen.Trash -> NoteAction.Trash
                NoteScreen.Notes -> null
            }
        }
    }

    private fun List<dev.logickoder.knote.notes.data.model.Note>.map(selected: List<dev.logickoder.knote.notes.data.model.NoteId>): ImmutableList<NoteItem> {
        return map {
            NoteItem(
                note = it,
                selected = it.id in selected
            )
        }.toImmutableList()
    }

    private fun List<dev.logickoder.knote.notes.data.model.Note>.sort(settings: Map<SettingsToggle, Boolean>): List<dev.logickoder.knote.notes.data.model.Note> {
        return if (settings[SettingsToggle.AddNewNotesToBottom] == true) {
            sortedBy { it.dateModified }
        } else sortedByDescending { it.dateModified }
    }
}

