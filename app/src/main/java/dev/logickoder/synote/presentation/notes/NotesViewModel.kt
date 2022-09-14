package dev.logickoder.synote.presentation.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumble.appyx.navmodel.backstack.BackStack
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.logickoder.synote.core.Navigation
import dev.logickoder.synote.data.model.NoteEntity
import dev.logickoder.synote.data.repository.AuthRepository
import dev.logickoder.synote.data.repository.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val repository: NotesRepository,
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val _notes = MutableStateFlow(emptyList<NoteEntity>())
    val notes: Flow<List<NoteEntity>>
        get() = _notes

    fun getNotes() {
        viewModelScope.launch {
            launch {
                val user = authRepository.currentUser.first()
                repository.refreshNotes(user!!.id)
            }
            launch {
                repository.notes.collectLatest {
                    _notes.emit(it)
                }
            }
        }
    }

    fun editNote(noteId: String?, backStack: BackStack<Navigation.Route>) {

    }

    fun deleteNote(noteId: String) {
        viewModelScope.launch {
            val user = authRepository.currentUser.first()
            repository.deleteNote(user!!.id, noteId)
        }
    }

    fun search(text: String) {
        viewModelScope.launch(Dispatchers.Main) {
            val notes = repository.notes.first().run {
                if (text.isNotBlank()) filter {
                    it.title.contains(text, ignoreCase = true)
                            || it.content.contains(text, ignoreCase = true)
                } else this
            }
            _notes.emit(notes)
        }
    }
}