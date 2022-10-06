package dev.logickoder.knote.notes.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.logickoder.knote.notes.api.NoteId
import dev.logickoder.knote.notes.data.domain.NoteScreen

@Composable
fun NotesRoute(
    modifier: Modifier = Modifier,
    screen: NoteScreen,
    onNoteClick: (NoteId?) -> Unit,
    openDrawer: () -> Unit,
) {
    val viewModel = viewModel<NotesViewModel>()
    val search by viewModel.search.collectAsState()
    val notes by viewModel.notes.collectAsState()
    val selected by viewModel.selected.collectAsState()

    LaunchedEffect(key1 = screen, block = {
        viewModel.setScreen(screen)
    })

    NotesScreen(
        modifier = modifier,
        notes = notes,
        search = search,
        selected = selected,
        screen = screen,
        editNote = onNoteClick,
        performAction = {
            viewModel.performAction(it)
        },
        openDrawer = openDrawer,
        onSearch = viewModel::search,
        onSelectedChanged = viewModel::toggleSelect,
        cancelSelection = viewModel::cancelSelection
    )
}