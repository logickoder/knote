package dev.logickoder.synote.edit_note.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.logickoder.synote.notes.api.Note
import dev.logickoder.synote.notes.api.NoteId
import dev.logickoder.synote.notes.api.NotesRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class EditNoteViewModel @Inject constructor(
    private val repository: NotesRepository,
) : ViewModel() {

    var title by mutableStateOf("")
        private set

    var content by mutableStateOf("")
        private set

    private var note: Note? = null

    fun getNote(id: NoteId?) {
        viewModelScope.launch {
            note = id?.let {
                repository.getNote(it).first()
            } ?: repository.createNote()
            title = note?.title ?: ""
            content = note?.content ?: ""
        }
    }

    fun updateTitle(title: String) {
        this.title = title
    }

    fun updateContent(content: String) {
        this.content = content
    }

    fun save() {
        note ?: return
        viewModelScope.launch {
            val id = repository.saveNote(
                note!!.copy(title = title, content = content)
            )
            note = repository.getNote(id).first()

        }
    }
}