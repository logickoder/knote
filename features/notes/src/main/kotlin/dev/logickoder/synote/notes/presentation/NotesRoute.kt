package dev.logickoder.synote.notes.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.logickoder.synote.notes.api.NoteId

@Composable
fun NotesRoute(
    modifier: Modifier = Modifier,
    onNoteClick: (NoteId?) -> Unit,
) {
    val viewModel = viewModel<NotesViewModel>()
    val search by viewModel.search.collectAsState()
    val notes by viewModel.notes.collectAsState()
    val inSelection by viewModel.inSelection.collectAsState()

    NotesScreen(
        modifier = modifier,
        notes = notes,
        search = search,
        inSelection = inSelection,
        editNote = onNoteClick,
        onSearch = viewModel::search,
        onSelectedChanged = viewModel::toggleSelect
    )
}