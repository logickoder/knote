package dev.logickoder.synote.presentation.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.operation.push
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

    init {
        viewModelScope.launch {
            authRepository.currentUser.collectLatest { user ->
                user?.let { repository.refreshNotes(it.id) }
            }
        }
    }

    private val filter = MutableStateFlow("")

    val notes = combine(
        repository.notes,
        filter,
        transform = { notes, filter -> notes.filter(filter) }
    ).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun editNote(id: String?, backStack: BackStack<Navigation.Route>) {
        backStack.push(Navigation.Route.EditNote(id))
    }

    fun deleteNote(id: String) {
        viewModelScope.launch {
            val user = authRepository.currentUser.first()
            repository.deleteNote(user!!.id, id)
        }
    }

    fun search(text: String) {
        viewModelScope.launch(Dispatchers.Main) {
            filter.emit(text)
        }
    }

    private fun List<NoteEntity>.filter(text: String): List<NoteEntity> {
        return if (text.isNotBlank()) filter {
            it.title.contains(text, ignoreCase = true)
                    || it.content.contains(text, ignoreCase = true)
        } else this
    }
}