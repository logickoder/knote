package dev.logickoder.knote.note_list.data.model

import androidx.compose.runtime.Stable
import dev.logickoder.knote.notes.data.model.Note

@Stable
internal data class NoteListItem(
    val note: Note,
    val selected: Boolean,
)
