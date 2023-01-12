package dev.logickoder.knote.edit_note.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.logickoder.knote.model.formatted
import dev.logickoder.knote.notes.data.model.Note
import dev.logickoder.knote.notes.data.model.NoteAction
import dev.logickoder.knote.notes.data.model.NoteId
import dev.logickoder.knote.notes.data.repository.NotesRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
internal class EditNoteViewModel @Inject constructor(
    private val repository: NotesRepository,
) : ViewModel() {

    var title by mutableStateOf("")
        private set

    var content by mutableStateOf("")
        private set

    var editedAt: String by mutableStateOf(LocalDateTime.now().formatted)
        private set

    private var note: Note? = null

    fun getNote(id: NoteId?) {
        viewModelScope.launch {
            note = id?.let {
                repository.getNote(it).first()
            } ?: repository.createNote()
            title = note?.title ?: ""
            content = note?.content ?: ""
            editedAt = (note?.dateModified ?: LocalDateTime.now()).formatted
        }
    }

    fun updateTitle(title: String) {
        this.title = title
    }

    fun updateContent(content: String) {
        this.content = content
    }

    fun performAction(action: NoteAction?) {
        viewModelScope.launch {
            repository.performAction(action, note!!.id)
        }
    }

    fun save() {
        note ?: return
        viewModelScope.launch {
            // only save the note if it contains changes
            if (note?.title != title || note?.content != content) {
                val id = repository.save(
                    note!!.copy(title = title, content = content)
                ).firstOrNull() ?: return@launch
                note = repository.getNote(id).first()
            }
        }
    }
}