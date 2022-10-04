package dev.logickoder.synote.notes.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.logickoder.synote.notes.api.Note
import dev.logickoder.synote.notes.api.NoteAction
import dev.logickoder.synote.notes.api.NoteId
import dev.logickoder.synote.notes.data.domain.NoteDomain
import dev.logickoder.synote.ui.NoteActionDialog
import dev.logickoder.synote.ui.theme.SynoteTheme
import dev.logickoder.synote.ui.theme.padding
import dev.logickoder.synote.ui.theme.secondaryPadding
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@Composable
internal fun NotesScreen(
    modifier: Modifier = Modifier,
    search: String,
    notes: ImmutableList<NoteDomain>,
    selected: Int,
    screen: NotesDrawerItem,
    editNote: (NoteId?) -> Unit,
    performAction: (NoteAction?) -> Unit,
    onSearch: (String) -> Unit,
    onSelectedChanged: (NoteId) -> Unit,
    onScreenChanged: (NotesDrawerItem) -> Unit,
    cancelSelection: () -> Unit,
) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

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
                        if (screen == NotesDrawerItem.Notes || it == NoteAction.Trash) {
                            showDialog = it
                        } else performAction(it)
                    },
                    cancelSelection = cancelSelection,
                )
            } else NotesAppBar(
                search = search,
                onSearch = onSearch,
                openDrawer = {
                    coroutineScope.launch {
                        scaffoldState.drawerState.open()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
            )

            showDialog?.let {
                NoteActionDialog(
                    text = stringResource(
                        id = dev.logickoder.synote.ui.R.string.ui_confirmation_multiple,
                        it.name.lowercase()
                    ),
                    confirmAction = {
                        performAction(it)
                    },
                    dismissDialog = { showDialog = null }
                )

            }
        },
        drawerContent = {
            NotesDrawer(
                selected = screen,
                itemClicked = {
                    coroutineScope.launch {
                        launch {
                            onScreenChanged(it)
                        }
                        scaffoldState.drawerState.close()
                    }
                }
            )
        },
        drawerShape = RoundedCornerShape(topEnd = 15.dp, bottomEnd = 15.dp),
        drawerBackgroundColor = MaterialTheme.colors.background,
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
private fun EmptyNotesScreenPreview() = SynoteTheme {
    NotesScreen(
        notes = persistentListOf(),
        search = "",
        selected = 0,
        screen = NotesDrawerItem.Notes,
        editNote = {},
        performAction = {},
        onSearch = {},
        onSelectedChanged = { },
        onScreenChanged = {},
        cancelSelection = {}
    )
}

@Preview
@Composable
private fun NotesScreenPreview() = SynoteTheme {
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
        screen = NotesDrawerItem.Notes,
        editNote = {},
        performAction = {},
        onSearch = {},
        onSelectedChanged = { },
        onScreenChanged = { },
        cancelSelection = {}
    )
}