package dev.logickoder.knote.note_list.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.logickoder.knote.note_list.data.model.NoteListScreen
import dev.logickoder.knote.notes.data.model.NoteId

@Composable
fun NoteListRoute(
    modifier: Modifier = Modifier,
    screen: NoteListScreen,
    onNoteClick: (NoteId?) -> Unit,
    openDrawer: () -> Unit,
) {
    val viewModel = viewModel<NoteListViewModel>()
    val search by viewModel.search.collectAsState()
    val notes by viewModel.notes.collectAsState()
    val selected by viewModel.selected.collectAsState()

    LaunchedEffect(key1 = screen, block = {
        viewModel.setScreen(screen)
    })

    NoteListScreen(
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