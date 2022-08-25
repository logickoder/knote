package dev.logickoder.synote.presentation.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumble.appyx.navmodel.backstack.BackStack
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.logickoder.synote.core.Navigation
import dev.logickoder.synote.data.model.NoteEntity
import dev.logickoder.synote.data.repository.AuthRepository
import dev.logickoder.synote.data.repository.NotesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val repository: NotesRepository,
    private val authRepository: AuthRepository,
) : ViewModel() {
    val notes: Flow<List<NoteEntity>>
        get() = repository.notes

    fun getNotes() {
        viewModelScope.launch {
            authRepository.currentUser.take(1).collectLatest {
                repository.refreshNotes(it!!.id)
            }
        }
    }

    fun editNote(noteId: String?, backStack: BackStack<Navigation.Route>) {

    }

    fun search(text: String) {

    }
}