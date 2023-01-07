package dev.logickoder.knote.notes.data.model

import androidx.compose.runtime.Stable

@Stable
internal data class NoteItem(
    val note: Note,
    val selected: Boolean,
)
