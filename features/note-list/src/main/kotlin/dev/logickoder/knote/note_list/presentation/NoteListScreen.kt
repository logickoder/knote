package dev.logickoder.knote.note_list.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import dev.logickoder.knote.note_list.data.model.NoteListItem
import dev.logickoder.knote.note_list.data.model.NoteListScreen
import dev.logickoder.knote.notes.data.model.NoteAction
import dev.logickoder.knote.notes.data.model.NoteId
import dev.logickoder.knote.ui.NoteActionDialog
import dev.logickoder.knote.ui.theme.KNoteTheme
import dev.logickoder.knote.ui.theme.padding
import dev.logickoder.knote.ui.theme.secondaryPadding
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import java.time.LocalDateTime

@Composable
internal fun NoteListScreen(
    modifier: Modifier = Modifier,
    search: String,
    notes: ImmutableList<NoteListItem>,
    selected: Int,
    screen: NoteListScreen,
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
                NoteListInSelectionAppBar(
                    selected = selected,
                    noteCount = notes.size,
                    screen = screen,
                    modifier = Modifier.fillMaxWidth(),
                    performAction = {
                        if (screen == NoteListScreen.Notes || it == NoteAction.Trash) {
                            showDialog = it
                        } else performAction(it)
                    },
                    cancelSelection = cancelSelection,
                )
            } else NoteListAppBar(
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
private fun EmptyNoteListScreenPreview() = KNoteTheme {
    NoteListScreen(
        notes = persistentListOf(),
        search = "",
        selected = 0,
        screen = NoteListScreen.Notes,
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
private fun NoteLisScreenPreview() = KNoteTheme {
    NoteListScreen(
        notes = (1..10).map {
            NoteListItem(
                note = dev.logickoder.knote.notes.data.model.Note(
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
        screen = NoteListScreen.Notes,
        editNote = {},
        performAction = {},
        onSearch = {},
        onSelectedChanged = { },
        cancelSelection = {},
        openDrawer = {},
    )
}