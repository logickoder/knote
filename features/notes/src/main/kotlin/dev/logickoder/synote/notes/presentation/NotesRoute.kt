package dev.logickoder.synote.notes.presentation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.logickoder.synote.notes.api.NoteId

@Composable
fun NotesRoute(
    modifier: Modifier = Modifier,
    isDarkMode: Boolean = isSystemInDarkTheme(),
    onNoteClick: (NoteId?) -> Unit,
    onSwitchDarkMode: () -> Unit,
) {
    val viewModel = viewModel<NotesViewModel>()
    val notes by viewModel.notes.collectAsState()

    NotesScreen(
        modifier = modifier,
        notes = notes,
        editNote = onNoteClick,
        onSearch = viewModel::search,
        isDarkMode = isDarkMode,
        switchDarkMode = onSwitchDarkMode,
    )
}