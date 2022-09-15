package dev.logickoder.synote.presentation.edit_note

import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import dev.logickoder.synote.data.model.NoteEntity

@Composable
fun NoteEditor(note: NoteEntity) {
    TextField(value = note.content, onValueChange = {})
}