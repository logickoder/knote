package dev.logickoder.knote.notes.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import dev.logickoder.knote.notes.api.Note
import dev.logickoder.knote.notes.api.NoteAction
import dev.logickoder.knote.notes.api.NoteId
import dev.logickoder.knote.notes.data.domain.NoteDomain
import dev.logickoder.knote.notes.data.domain.NoteScreen
import dev.logickoder.knote.ui.NoteActionDialog
import dev.logickoder.knote.ui.theme.KNoteTheme
import dev.logickoder.knote.ui.theme.padding
import dev.logickoder.knote.ui.theme.secondaryPadding
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import java.time.LocalDateTime

@Composable
internal fun NotesScreen(
    modifier: Modifier = Modifier,
    search: String,
    notes: ImmutableList<NoteDomain>,
    selected: Int,
    screen: NoteScreen,
    editNote: (NoteId?) -> Unit,
    performAction: (NoteAction?) -> Unit,
    openDrawer: () -> Unit,
    onSearch: (String) -> Unit,
    onSelectedChanged: (NoteId) -> Unit,
    cancelSelection: () -> Unit,
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        topBar = {
            var showDialog by remember { mutableStateOf<NoteAction?>(null) }

            if (selected > 0) {
                NotesInSelectionAppBar(
                    selected = selected,
                    noteCount = notes.size,
                    screen = screen,
                    modifier = Modifier.fillMaxWidth(),
                    performAction = {
                        if (screen == NoteScreen.Notes || it == NoteAction.Trash) {
                            showDialog = it
                        } else performAction(it)
                    },
                    cancelSelection = cancelSelection,
                )
            } else NotesAppBar(
                search = search,
                onSearch = onSearch,
                openDrawer = openDrawer,
                modifier = Modifier.fillMaxWidth(),
            )

            showDialog?.let {
                NoteActionDialog(
                    text = stringResource(
                        id = dev.logickoder.knote.ui.R.string.ui_confirmation_multiple,
                        it.name.lowercase()
                    ),
                    confirmAction = {
                        performAction(it)
                    },
                    dismissDialog = { showDialog = null }
                )

            }
        },
        content = { scaffoldPadding ->
            LazyColumn(
                modifier = Modifier.padding(scaffoldPadding),
                contentPadding = PaddingValues(padding()),
                content = {
                    items(notes) { note ->
                        Note(
                            domain = note,
                            editNote = editNote,
                            selectedChanged = onSelectedChanged,
                            inSelection = selected > 0,
                        )
                        Spacer(modifier = Modifier.height(secondaryPadding()))
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { editNote(null) },
                backgroundColor = MaterialTheme.colors.primary,
                content = {
                    Icon(
                        painter = rememberVectorPainter(image = Icons.Outlined.Add),
                        contentDescription = null
                    )
                }
            )
        }
    )
}

@Preview
@Composable
private fun EmptyNotesScreenPreview() = KNoteTheme {
    NotesScreen(
        notes = persistentListOf(),
        search = "",
        selected = 0,
        screen = NoteScreen.Notes,
        editNote = {},
        performAction = {},
        onSearch = {},
        onSelectedChanged = {},
        cancelSelection = {},
        openDrawer = {},
    )
}

@Preview
@Composable
private fun NotesScreenPreview() = KNoteTheme {
    NotesScreen(
        notes = (1..10).map {
            NoteDomain(
                note = Note(
                    id = NoteId(it.toLong()),
                    title = "Title $it",
                    content = "Content $it",
                    dateCreated = LocalDateTime.now(),
                    action = null,
                ),
                selected = it % 2 == 0,
            )
        }.toImmutableList(),
        search = "My Note",
        selected = 1,
        screen = NoteScreen.Notes,
        editNote = {},
        performAction = {},
        onSearch = {},
        onSelectedChanged = { },
        cancelSelection = {},
        openDrawer = {},
    )
}